package things.connectors.mongo;

/**
 * Implementation of {@link things.thing.ThingReader} that uses MongoDB to persist and
 * lookup {@link things.thing.Thing}s.
 */
public class MongoReader {

//	private static final Logger myLogger = LoggerFactory
//			.getLogger(MongoReader.class);
//
//	private MongoTemplate mongoTemplate;
//
//    public MongoReader(MongoTemplate mt) {
//        this.mongoTemplate = mt;
//    }
//
//    @Override
//	public Observable<Thing> findAllThings() {
//		List<Thing> things = mongoTemplate.findAll(Thing.class);
//		return Observable.from(things);
//	}
//
//
//	private Optional<Thing> findThingById(Object id) {
//
//		if (id == null) {
//			throw new IllegalArgumentException("Id can't be null");
//		}
//
//		Query q = new Query();
//		try {
//			q.addCriteria(Criteria.where("_id").is(new ObjectId((String)id)));
//		} catch (IllegalArgumentException iae) {
//			throw new NoSuchThingException(id);
//		}
//
//		Thing thing = mongoTemplate.findOne(q, Thing.class);
//
//		if (thing == null) {
//			return Optional.empty();
//		}
//
//		return Optional.of(thing);
//	}
//
//
//    @Override
//    public Observable<Thing> findThingsMatchingTypeAndKey(String type, String key) {
//
//        if ( "*".equals(key) ) {
//            Query q = new Query();
//
//            String regex = MatcherUtils.convertGlobToRegex(type);
//
//            q.addCriteria(Criteria.where("type").regex(regex));
//
//            List<Thing> things = mongoTemplate.find(q, Thing.class);
//            return Observable.from(things);
//        } else {
//
//            Query q = new Query();
//            String regexType = MatcherUtils.convertGlobToRegex(type);
//            String regexKey = MatcherUtils.convertGlobToRegex(key);
//            q.addCriteria(Criteria.where("type").regex(regexType).and("key").regex(regexKey));
//
//            List<Thing> things = mongoTemplate.find(q, Thing.class);
//
//            return Observable.from(things);
//        }
//    }
//
//
//
//
//    @Override
//    public <V> Observable<Thing<V>> findThingsByKeyAndValue(String key, V value) {
//        return null;
//    }
//
//    private MongoPersistentProperty getIdField(Object value) {
//        MongoPersistentEntity<?> mongoPersistentEntity = mongoTemplate.getConverter()
//                .getMappingContext().getPersistentEntity(value.getClass());
//        MongoPersistentProperty id = mongoPersistentEntity.getIdProperty();
//        return id;
//    }
//
//    private Object extractId(MongoPersistentProperty id, Object value) {
//
//        try {
//            Field idField = id.getField();
//            Object idValue = idField.get(value);
//            return idValue;
//        } catch (IllegalAccessException e) {
//            throw new TypeRuntimeException("Can't extract id from type "+TypeRegistry.getType(value), TypeRegistry.getType(value), e);
//        }
//    }
//
//    @Override
//    public <V> Observable<Thing<V>> createThing(String key, V value) {
//
//		myLogger.debug("Saving thing {}/{}", TypeRegistry.getType(value), key);
//
//        Object value = null;
//        if ( TypeRegistry.isSimpleValue(value) ) {
//            value = ((SimpleValue)value).getValue();
//        } else {
//            MongoPersistentProperty idField = getIdField(value);
//            if ( idField == null ) {
//                IdWrapper wrapper = new IdWrapper(value);
//                mongoTemplate.save(wrapper, TypeRegistry.getType(value));
//                value = wrapper.getId();
//            } else {
//                mongoTemplate.save(value, TypeRegistry.getType(value));
//                value = extractId(idField, value);
//            }
//        }
//        String type = TypeRegistry.getType(value);
//        Thing t = new Thing();
//        t.setKey(key);
//        t.setThingType(type);
//        t.setValue(value);
//		mongoTemplate.save(t);
//
//        return Observable.from(t);
//	}
    //    @Override
//    public List<Thing> findThingsByOtherThingId(String id) {
//        Query q = new Query();
//
//        q.addCriteria(Criteria.where("otherThings").is(id));
//
//        List<Thing> result = mongoTemplate.find(q, Thing.class);
//        return result;
//    }
//
//    @Override
//	public List<Thing> getOtherThings(Thing t) {
//
//		Query q = new Query();
//		q.addCriteria(Criteria.where("_id").in(t.getOtherThings()));
//
//		List<Thing> result = mongoTemplate.find(q, Thing.class);
//		return result;
//
//	}

//
//    @Override
//	public List<Thing> findThingsByTypeAndValueId(String type, String value, boolean allowGlob) {
//
//		if (value == null) {
//			throw new IllegalArgumentException("Id can't be null");
//		}
//
//        if (! allowGlob ) {
//            Query q = new Query();
//
//            q.addCriteria(Criteria.where("value").is(value)
//                    .and("type").is(type));
//
//            List<Thing> result = mongoTemplate.find(q, Thing.class);
//
//            return result;
//        } else {
//            String regex = MatcherUtils.convertGlobToRegex(value);
//            Query q = new Query();
//
//            q.addCriteria(Criteria.where("value").regex(regex)
//                    .and("type").is(type));
//
//            List<Thing> result = mongoTemplate.find(q, Thing.class);
//
//            return result;
//        }
//
//	}
//    @Override
//    public Stream<Thing> getOtherThingsStreamByTypeAndKey(Thing t, String type, String key) throws NoSuchThingException {
//
//        if ( "*".equals(key) ) {
//            Query q = new Query();
//
//            String regex = MatcherUtils.convertGlobToRegex(type);
//
//            q.addCriteria(Criteria.where("_id").in(t.getOtherThings()).and("type").regex(regex));
//
//            List<Thing> things = mongoTemplate.find(q, Thing.class);
//            return things.parallelStream();
//        } else if ( "*".equals(type) ) {
//            Query q = new Query();
//
//            String regex = MatcherUtils.convertGlobToRegex(key);
//
//            q.addCriteria(Criteria.where("_id").in(t.getOtherThings()).and("key").regex(regex));
//
//            List<Thing> things = mongoTemplate.find(q, Thing.class);
//            return things.parallelStream();
//        } else {
//            Query q = new Query();
//            String regexType = MatcherUtils.convertGlobToRegex(type);
//            String regexKey = MatcherUtils.convertGlobToRegex(key);
//            q.addCriteria(Criteria.where("_id").in(t.getOtherThings()).and("type").regex(regexType).and("key").regex(regexKey));
//
//            List<Thing> things = mongoTemplate.find(q, Thing.class);
//
//            return things.parallelStream();
//        }
//
//    }

//        @Override
//    public Set<String> lookupThingsForValue(Object value) {
//
//        Class<?> typeClass = value.getClass();
//
//        BasicDBObject mongoDbObject = (BasicDBObject) mongoTemplate.getConverter()
//                .convertToMongoType(value);
//
//        // remove non-populated (null) fields
//        List<String> keysToRemove = Lists.newLinkedList();
//        for (String key : mongoDbObject.keySet()) {
//            if (mongoDbObject.get(key) == null) {
//                keysToRemove.add(key);
//            }
//        }
//        for (String key : keysToRemove) {
//            mongoDbObject.remove(key);
//        }
//
//        BasicQuery bq = new BasicQuery(mongoDbObject.toString());
//
//        List<PersistentValue> listWithIds = (List<PersistentValue>) mongoTemplate.find(bq, typeClass);
//
//        Set<String> ids = listWithIds.stream().map(v -> v.getId()).collect(Collectors.toSet());
//
//        return ids;
//
////        List<Thing> result = findThingsByObjectIds(ids);
////
////        return result;
//    }


//    //@Override
//    public List<Thing> getOtherThingsByTypeAndKey(Thing parent, String type, String key) {
//
//        String typeRegex = MatcherUtils.convertGlobToRegex(type);
//        Query q = new Query();
//
//        q.addCriteria(Criteria.where("_id").in(parent.getOtherThings())
//                .and("type").regex(typeRegex));
//        List<Thing> result = mongoTemplate.find(q, Thing.class);
//
//        //TODO can we have 2 different queries?
//
//        if ( "*".equals(key) ) {
//            return result;
//        } else {
//            return result.parallelStream().filter(thing -> MatcherUtils.wildCardMatch(thing.getKey(), key)).collect(Collectors.toList());
//        }
//
//    }
//
//    public PersistentValue loadPersistentValue(
//            Class<?> typeClass, String id) {
//
//        Query q = new Query();
//        q.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
//
//        PersistentValue v = (PersistentValue) mongoTemplate.findOne(q, typeClass);
//
//        return v;
//
//    }




}
