package things.jpa;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import rx.Observable;
import rx.Subscriber;
import things.exceptions.QueryRuntimeException;
import things.exceptions.TypeRuntimeException;
import things.thing.AbstractThingReader;
import things.thing.Thing;
import things.thing.ThingReader;
import things.thing.ThingWriter;
import things.utils.MatcherUtils;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * Created by markus on 20/05/14.
 */
public class JpaConnector extends AbstractThingReader implements ThingReader, ThingWriter {

    public static Logger myLogger = LoggerFactory.getLogger(JpaConnector.class);

    @Autowired
    protected EntityManager entityManager;
    private Timer find_all_timer;
    private Timer find_for_key_timer;
    private Timer find_for_type_and_key_timer;
    private Timer find_for_type_timer;
    private Timer find_parents_timer;
    @Autowired
    protected MetricRegistry metrics;
    private Timer read_value_timer;
    private Timer save_value_timer;
    @Autowired
    private ThingRepository thingRepository;
    @Autowired
    private ValueRepositories valueRepositories;

    @Override
    public <V> Thing<V> addChild(Thing<?> parent, Thing<V> child) {
        child.getParents().add(parent.getId());
        return saveThing(child);
    }

    /**
     * For testing only
     */
    public void deleteAll() {
        thingRepository.deleteAll();
        for ( ValueRepository vr : valueRepositories.getAll() ) {
            ((CrudRepository) vr).deleteAll();
        }
    }

    @Override
    public boolean deleteThing(String id, Optional<String> type, Optional<String> key) {
        Thing<?> temp = thingRepository.findOne(id);
        if ( temp == null ) {
            return false;
        }
        //TODO delete value
        thingRepository.delete(id);
        return true;
    }

    @Override
    public Observable<? extends Thing<?>> findAllThings() {
        final Timer.Context context = find_all_timer.time();
        try {
            return Observable.from(thingRepository.findAll());
        } finally {
            context.stop();
        }
    }

    @Override
    public Observable<? extends Thing<?>> findThingForId(String id) {
        Thing<?> t = thingRepository.findOne(id);
        return Observable.just(t);
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForKey(String type) {

        final Timer.Context context = find_for_key_timer.time();

        try {
            Iterable<Thing<?>> things = thingRepository.findByKey(type);
            return Observable.from(things);
        } finally {
            context.stop();
        }
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForType(String type) {

        final Timer.Context context = find_for_type_timer.time();

        try {
            Iterable<? extends Thing<?>> things = thingRepository.findByThingType(type);
            return Observable.from(things);
        } catch (Exception e) {
            myLogger.debug("Query Exception when querying for type " + type + ": " + e.getLocalizedMessage(), e);
            throw new QueryRuntimeException("Can't query for type: " + type, e);
        } finally {
            context.stop();
        }
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForTypeAndKey(String type, String key) {

        final Timer.Context context = find_for_type_and_key_timer.time();
        try {
            Iterable<Thing<?>> things = thingRepository.findByTypeAndKey(type, key);
            return Observable.from(things);
        } finally {
            context.stop();
        }
    }

    public Observable<? extends Thing<?>> findThingsMatchingTypeAndKey(final String type,
                                                                       final String key) {

        Observable obs = Observable.create((Subscriber<? super Object> subscriber) -> {

            findAllThings().subscribe(
                    (thing) -> {
                        if ( MatcherUtils.wildCardMatch(thing.getThingType(), type)
                                && MatcherUtils.wildCardMatch(thing.getKey(), key) ) {
                            subscriber.onNext(thing);
                        }
                    },
                    (throwable) -> {
                        subscriber.onError(throwable);
                    },
                    () -> subscriber.onCompleted()
            );
        });
        return obs;
    }

    @Override
    public Observable<? extends Thing<?>> getChildrenForId(String id) {

        final Timer.Context context = find_parents_timer.time();

        try {
            Iterable<Thing<?>> result = thingRepository.findByParents(id);

            return Observable.from(result);
        } finally {
            context.stop();
        }
    }

    @Override
    public Observable<? extends Thing<?>> getChildrenMatchingTypeAndKey(Observable<? extends Thing<?>> things, String typeMatcher, String keyMatcher) {

        Observable result = things.flatMap(t -> getChildrenForId(t.getId()))
                .filter(t -> MatcherUtils.wildCardMatch(t.getThingType(), typeMatcher)
                        && MatcherUtils.wildCardMatch(t.getKey(), keyMatcher));

        return result;
    }

    private String getId(Object value) {

        try {
            String idProperty = getIdProperty(value.getClass());
            Field idField = value.getClass().getDeclaredField(idProperty);
            idField.setAccessible(true);
            Object id = idField.get(value);
            return (String) id;

        } catch (Exception e) {
            throw new TypeRuntimeException("Can't retrieve id from type " + typeRegistry.getType(value.getClass()), value.getClass(), e);
        }


    }

    private String getIdProperty(Class entityClass) {
        String idProperty = null;
        Metamodel metamodel = entityManager.getMetamodel();
        EntityType entity = metamodel.entity(entityClass);
        Set<SingularAttribute> singularAttributes = entity.getSingularAttributes();
        for ( SingularAttribute singularAttribute : singularAttributes ) {
            if ( singularAttribute.isId() ) {
                idProperty = singularAttribute.getName();
                break;
            }
        }
        if ( idProperty == null )
            throw new RuntimeException("id field not found");
        return idProperty;
    }

    @Override
    public <V> V readValue(Thing<V> thing) {


        if ( typeRegistry.convertsFromString(thing.getThingType()) ) {
            Optional<V> value = (Optional<V>) typeRegistry.convertFromString(thing.getThingType(), (String) thing.getValue());
            if ( !value.isPresent() ) {
                throw new TypeRuntimeException("Could not convert string to value for type " + thing.getThingType(), thing.getThingType());
            }
            return value.get();
        } else {
            final Timer.Context context = read_value_timer.time();
            try {
                ValueRepository repo = valueRepositories.get(thing.getThingType());

                V value = (V) repo.findOne(thing.getValue());
                return value;
            } finally {
                context.stop();
            }
        }

    }

    @Override
    public <V> Thing<V> saveThing(Thing<V> t) {

        // check whether we have to save the value extra
        if ( t.getValueIsPopulated() ) {

            if ( typeRegistry.convertsFromString(t.getThingType()) ) {
                Optional<String> valueString = typeRegistry.convertToString(t.getValue());
                if ( !valueString.isPresent() ) {
                    throw new TypeRuntimeException("Could not convert value with type " + t.getThingType() + " to string.", t.getThingType());
                }
                t.setValueIsPopulated(false);
                t.setValue((V) valueString.get());
            } else {
                V value = t.getValue();
                String id = saveValue(value);
                t.setValueIsPopulated(false);
                t.setValue((V) id);
            }
        }

        Thing<V> newThing = thingRepository.save(t);

        return newThing;
    }

    private String saveValue(Object value) {
        final Timer.Context context = save_value_timer.time();
        try {

            ValueRepository repo = valueRepositories.get(typeRegistry.getType(value));
            Object newValue = repo.save(value);
            String id = getId(newValue);
            return id;
        } finally {
            context.stop();
        }
    }

    @Autowired
    public void setMetricRegistry(MetricRegistry reg) {
        this.metrics = reg;
        find_all_timer = metrics.timer(name(JpaConnector.class, "find-all"));
        find_parents_timer = metrics.timer(name(JpaConnector.class, "find-parents"));
        find_for_type_timer = metrics.timer(name(JpaConnector.class, "find-for-type"));
        find_for_key_timer = metrics.timer(name(JpaConnector.class, "find-for-key"));
        find_for_type_and_key_timer = metrics.timer(name(JpaConnector.class, "find-for-type-and-key"));
        read_value_timer = metrics.timer(name(JpaConnector.class, "read-value"));
        save_value_timer = metrics.timer(name(JpaConnector.class, "save-value"));

    }

    public void setThingRepository(ThingRepository thingRepository) {
        this.thingRepository = thingRepository;
    }

    public void setValueRepositories(ValueRepositories valueRepositories) {
        this.valueRepositories = valueRepositories;
    }
}
