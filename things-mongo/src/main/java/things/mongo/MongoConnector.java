package things.mongo;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import rx.Observable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import things.connectors.IdWrapper;
import things.exceptions.NoSuchThingException;
import things.exceptions.TypeRuntimeException;
import things.thing.AbstractThingReader;
import things.thing.Thing;
import things.thing.ThingReader;
import things.thing.ThingWriter;
import things.utils.MatcherUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 10/05/14
 * Time: 10:58 PM
 */
public class MongoConnector extends AbstractThingReader implements ThingReader, ThingWriter {


    private static final Logger myLogger = LoggerFactory
            .getLogger(MongoConnector.class);
    private Timer find_all_timer;
    private Timer find_children_matching_timer;
    private Timer find_matching_timer;
    @Autowired
    private MetricRegistry metrics;
    private MongoTemplate mongoTemplate;


    @Autowired
    public MongoConnector(MongoTemplate mongoTemplate) throws Exception {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean deleteThing(String id, Optional<String> type, Optional<String> key) {

        Optional<Thing<?>> thing = findThingForIdQuery(id);

        if ( !thing.isPresent() ) {
            return false;
        } else {
            Query q = new Query();
            try {
                q.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
            } catch (IllegalArgumentException iae) {
                throw new NoSuchThingException(id);
            }
            mongoTemplate.remove(thing.get());
            return true;
        }
    }

    private Object extractId(MongoPersistentProperty id, Object value) {

        try {
            Field idField = id.getField();
            String idValue = (String) idField.get(value);
            return idValue;
        } catch (IllegalAccessException e) {
            throw new TypeRuntimeException("Can't extract id from type " + typeRegistry.getType(value), typeRegistry.getType(value), e);
        } catch (ClassCastException cce) {
            throw new TypeRuntimeException("Can't extract id for type " + typeRegistry.getType(value) + ": id is not of String type", typeRegistry.getType(value));
        }
    }

    @Override
    public Observable<? extends Thing<?>> findAllThings() {
        final Timer.Context context = find_all_timer.time();
        try {
            List<Thing> things = mongoTemplate.findAll(Thing.class);
            return Observable.from(things).map(t -> (Thing<?>) t);
        } finally {
            context.stop();
        }
    }

    @Override
    public Observable<? extends Thing<?>> findThingForId(String id) {

        Optional<Thing<?>> thing = findThingForIdQuery(id);

        if ( thing.isPresent() ) {
            return Observable.just(thing.get());
        } else {
            throw new NoSuchThingException(id);
        }
    }

    private Optional<Thing<?>> findThingForIdQuery(String id) {
        if ( id == null ) {
            throw new IllegalArgumentException("Id can't be null");
        }

        Query q = new Query();
        try {
            q.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        } catch (IllegalArgumentException iae) {
            throw new NoSuchThingException(id);
        }

        Thing thing = mongoTemplate.findOne(q, Thing.class);

        if ( thing == null ) {
            return Optional.empty();
        }
        return Optional.of(thing);
    }

    @Override
    public Observable<? extends Thing<?>> findThingsMatchingTypeAndKey(final String type,
                                                                       final String key) {

        final Timer.Context context = find_matching_timer.time();
        try {
            Query q = new Query();
            String regexType = MatcherUtils.convertGlobToRegex(type);
            String regexKey = MatcherUtils.convertGlobToRegex(key);

            q.addCriteria(Criteria.where("thingType").regex(regexType).and("key").regex(regexKey));

            List<Thing> things = mongoTemplate.find(q, Thing.class);

            return Observable.from(things).map(t -> (Thing<?>) t);
        } finally {
            context.stop();
        }

    }

    public Observable<? extends Thing<?>> getChildrenMatchingTypeAndKey(Thing<?> thing, String typeMatcher, String keyMatcher) {
        Query q = new Query();
        String regexType = MatcherUtils.convertGlobToRegex(typeMatcher);
        String regexKey = MatcherUtils.convertGlobToRegex(keyMatcher);
        q.addCriteria(Criteria.where("parents").is(thing.getId()).and("thingType").regex(regexType).and("key").regex(regexKey));


        List<Thing> things = mongoTemplate.find(q, Thing.class);
        return Observable.from(things).map(t -> (Thing<?>) t);
    }

    @Override
    public Observable<? extends Thing<?>> getChildrenMatchingTypeAndKey(Observable<? extends Thing<?>> things, String typeMatcher, String keyMatcher) {

        return things.flatMap(t -> getChildrenMatchingTypeAndKey(t, typeMatcher, keyMatcher));
    }

    @Override
    public Observable<? extends Thing<?>> getChildrenForId(String id) {

        Query q = new Query();

        q.addCriteria(Criteria.where("parents").is(id));

        List<Thing> result = mongoTemplate.find(q, Thing.class);
        return Observable.from(result).map(t -> (Thing<?>)t);
    }

    private MongoPersistentProperty getIdField(Class valueClass) {
        MongoPersistentEntity<?> mongoPersistentEntity = mongoTemplate.getConverter()
                .getMappingContext().getPersistentEntity(valueClass);
        MongoPersistentProperty id = mongoPersistentEntity.getIdProperty();
        return id;
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    private boolean hasUsableId(Class valueClass) {
        if ( getIdField(valueClass) == null ) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public <V> V readValue(Thing<V> t) {

        return t.getValue();

//        Query q = new Query();
//        q.addCriteria(Criteria.where("_id").is(new ObjectId((String) t.getValue())));
////
//        Class typeClass = typeRegistry.getTypeClass(t.getThingType());
//        if ( hasUsableId(typeClass) ) {
//            Object v = mongoTemplate.findOne(q, typeClass);
//            return (V) v;
//        } else {
//            Object v = mongoTemplate.findOne(q, IdWrapper.class, t.getThingType());
//            return (V) ((IdWrapper) v).getValue();
//
//        }
    }

    @Override
    public <V> Thing<V> saveThing(Thing<V> t) {
        t.setValueIsPopulated(true);
        myLogger.debug("Saving thing: " + t.toString());
        // mongo gives it an id if necessary
        mongoTemplate.save(t);
        myLogger.debug("Saved: " + t.toString());
        return t;
    }

    public Object saveValue(Optional valueId, Object value) {
        myLogger.debug("Saving value: " + value);

        Object vId = null;

        MongoPersistentProperty idField = getIdField(value.getClass());
        if ( idField == null ) {
            IdWrapper wrapper = new IdWrapper(value);
            mongoTemplate.save(wrapper, typeRegistry.getType(value));
            vId = wrapper.getId();
        } else {
            mongoTemplate.save(value, typeRegistry.getType(value));
            vId = extractId(idField, value);
        }

        return vId;
    }

    @Autowired
    public void setMetricRegistry(MetricRegistry reg) {
        this.metrics = reg;
        find_all_timer = metrics.timer(name(MongoConnector.class, "find-all"));
        find_matching_timer = metrics.timer(name(MongoConnector.class, "find-matching"));
        find_children_matching_timer = metrics.timer(name(MongoConnector.class, "find-matching"));
    }

}
