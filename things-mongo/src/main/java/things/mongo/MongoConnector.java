package things.mongo;

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
import things.connectors.mongo.IdWrapper;
import things.exceptions.NoSuchThingException;
import things.exceptions.TypeRuntimeException;
import things.thing.Thing;
import things.thing.ThingReader;
import things.thing.ThingWriter;
import things.thing.TypeRegistry;
import things.utils.MatcherUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 10/05/14
 * Time: 10:58 PM
 */
public class MongoConnector implements ThingReader, ThingWriter {



    private static final Logger myLogger = LoggerFactory
			.getLogger(MongoConnector.class);

	@Autowired
	private MongoTemplate mongoTemplate;

    public MongoConnector(MongoTemplate mongoTemplate) throws Exception {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Observable<? extends Thing<?>> findAllThings() {
        List<Thing> things = mongoTemplate.findAll(Thing.class);
		return Observable.from(things).map(t -> (Thing<?>)t);
    }
    
    @Override
    public Observable<? extends Thing<?>> findThingForId(String id) {

		if (id == null) {
			throw new IllegalArgumentException("Id can't be null");
		}

		Query q = new Query();
		try {
			q.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
		} catch (IllegalArgumentException iae) {
			throw new NoSuchThingException(id);
		}

		Thing thing = mongoTemplate.findOne(q, Thing.class);

		if (thing == null) {
			throw new NoSuchThingException(id);
		}

		return Observable.just((Thing<?>)thing);
	}

    public Observable<? extends Thing<?>> getChildrenMatchingTypeAndKey(Thing<?> thing, String typeMatcher, String keyMatcher) {
        Query q = new Query();
        String regexType = MatcherUtils.convertGlobToRegex(typeMatcher);
        String regexKey = MatcherUtils.convertGlobToRegex(keyMatcher);
        q.addCriteria(Criteria.where("parents").is(thing.getId()).and("thingType").regex(regexType).and("key").regex(regexKey));


        List<Thing> things = mongoTemplate.find(q, Thing.class);
        return Observable.from(things).map(t -> (Thing<?>)t);
    }

    @Override
    public Observable<? extends Thing<?>> getChildrenMatchingTypeAndKey(Observable<? extends Thing<?>> things, String typeMatcher, String keyMatcher) {

        return things.flatMap(t -> getChildrenMatchingTypeAndKey(t, typeMatcher, keyMatcher));
    }

    public Observable<? extends Thing<?>> findThingsMatchingTypeAndKey(final String type,
                                                           final String key) {

        Query q = new Query();
        String regexType = MatcherUtils.convertGlobToRegex(type);
        String regexKey = MatcherUtils.convertGlobToRegex(key);
        
        q.addCriteria(Criteria.where("thingType").regex(regexType).and("key").regex(regexKey));

        List<Thing> things = mongoTemplate.find(q, Thing.class);

        return Observable.from(things).map(t -> (Thing<?>)t);

    }


    @Override
    public <V> V readValue(Thing<V> t) {
        Query q = new Query();
        q.addCriteria(Criteria.where("_id").is(new ObjectId((String)t.getValue())));
//
        Class typeClass = TypeRegistry.getTypeClass(t.getThingType());
        if (hasUsableId(typeClass)) {
            Object v = mongoTemplate.findOne(q, typeClass);
            return (V) v;
        } else {
            Object v = mongoTemplate.findOne(q, IdWrapper.class, t.getThingType());
            return (V) ((IdWrapper)v).getValue();

        }
    }

    @Override
    public <V> Thing<V> saveThing(Thing<V> t) {
        myLogger.debug("Saving thing: "+t.toString());
        // mongo gives it an id if necessary
        mongoTemplate.save(t);
        myLogger.debug("Saved: "+t.toString());
        return t;
    }

    private boolean hasUsableId(Class valueClass) {
        if (getIdField(valueClass) == null) {
            return false;
        } else {
            return true;
        }
    }

    private MongoPersistentProperty getIdField(Class valueClass) {
        MongoPersistentEntity<?> mongoPersistentEntity = mongoTemplate.getConverter()
                .getMappingContext().getPersistentEntity(valueClass);
        MongoPersistentProperty id = mongoPersistentEntity.getIdProperty();
        return id;
    }

    private static Object extractId(MongoPersistentProperty id, Object value) {

        try {
            Field idField = id.getField();
            String idValue = (String) idField.get(value);
            return idValue;
        } catch (IllegalAccessException e) {
            throw new TypeRuntimeException("Can't extract id from type "+TypeRegistry.getType(value), TypeRegistry.getType(value), e);
        } catch (ClassCastException cce) {
            throw new TypeRuntimeException("Can't extract id for type "+TypeRegistry.getType(value)+": id is not of String type", TypeRegistry.getType(value));
        }
    }

    @Override
    public Object saveValue(Optional valueId, Object value) {
        myLogger.debug("Saving value: "+value);

        Object vId = null;
        if ( TypeRegistry.isSimpleValue(value) ) {
            vId = value;
        } else {
            MongoPersistentProperty idField = getIdField(value.getClass());
            if ( idField == null ) {
                IdWrapper wrapper = new IdWrapper(value);
                mongoTemplate.save(wrapper, TypeRegistry.getType(value));
                vId = wrapper.getId();
            } else {
                mongoTemplate.save(value, TypeRegistry.getType(value));
                vId = extractId(idField, value);
            }
        }
        return vId;
    }

}
