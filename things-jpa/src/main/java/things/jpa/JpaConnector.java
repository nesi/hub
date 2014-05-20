package things.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;
import rx.Subscriber;
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

/**
 * Created by markus on 20/05/14.
 */
public class JpaConnector extends AbstractThingReader implements ThingReader, ThingWriter {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ThingRepository thingRepository;
    @Autowired
    private ValueRepositories valueRepositories;

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
        return Observable.from(thingRepository.findAll());
    }

    @Override
    public Observable<? extends Thing<?>> findThingForId(String id) {
        Thing<?> t = thingRepository.findOne(id);
        return Observable.just(t);
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForKey(String type) {
        Iterable<Thing<?>> things = thingRepository.findByKey(type);
        return Observable.from(things);
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForType(String type) {
        Iterable<Thing<?>> things = thingRepository.findByThingType(type);
        return Observable.from(things);
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForTypeAndKey(String type, String key) {
        Iterable<Thing<?>> things = thingRepository.findByTypeAndKey(type, key);
        return Observable.from(things);
    }

    @Override
    public Observable<? extends Thing<?>> findThingsMatchingTypeAndKey(String type, String key) {
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
            ValueRepository repo = valueRepositories.get(thing.getThingType());

            V value = (V) repo.findOne(thing.getValue());
            return value;
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
        ValueRepository repo = valueRepositories.get(typeRegistry.getType(value));
        Object newValue = repo.save(value);
        String id = getId(newValue);
        return id;
    }

    public void setThingRepository(ThingRepository thingRepository) {
        this.thingRepository = thingRepository;
    }

    public void setValueRepositories(ValueRepositories valueRepositories) {
        this.valueRepositories = valueRepositories;
    }
}
