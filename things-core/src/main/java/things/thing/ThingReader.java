package things.thing;


import rx.Observable;


/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 4/05/14
 * Time: 10:13 PM
 */
public interface ThingReader {

    abstract Observable<? extends Thing<?>> findThingsMatchingType(String typeMatcher);
    abstract Observable<? extends Thing<?>> findThingsMatchingKey(String keyMatcher);
    abstract Observable<? extends Thing<?>> findThingsForType(String type);
    abstract Observable<? extends Thing<?>> findThingsForKey(String key);
    abstract Observable<? extends Thing<?>> findThingsForTypeAndKey(String type, String key);
    abstract Observable<? extends Thing<?>> findThingForId(String id);
    abstract Observable<? extends Thing<?>> getChildrenForId(String id);

//    default Observable<? extends Thing<?>> getChildrenMatchingType(Observable<? extends Thing<?>> things, String typeMatcher) {
//        return getChildrenMatchingTypeAndKey(things, typeMatcher, "*");
//    }
//
//    default Observable<? extends Thing<?>> getChildrenForType(Observable<? extends Thing<?>> things, String type) {
//        if ( MatcherUtils.isGlob(type)) {
//            throw new ThingRuntimeException("Type can't be glob for this query");
//        }
//        return getChildrenMatchingTypeAndKey(things, type, "*");
//    }
//
//    default Observable<? extends Thing<?>> getChildrenMatchingKey(Observable<? extends Thing<?>> things, String keyMatcher) {
//        return getChildrenMatchingTypeAndKey(things, "*", keyMatcher);
//    }
//
//    default Observable<? extends Thing<?>> getChildrenForKey(Observable<? extends Thing<?>> things, String key) {
//        if (MatcherUtils.isGlob(key)) {
//            throw new ThingRuntimeException("Key can't be glob for this query");
//        }
//        return getChildrenMatchingTypeAndKey(things, "*", key);
//    }
//
//    default Observable<? extends Thing<?>> getChildrenForTypeAndKey(Observable<? extends Thing<?>> things, String type, String key) {
//        if (MatcherUtils.isGlob(type)) {
//            throw new ThingRuntimeException("Type can't be glob for this query");
//        }
//        if (MatcherUtils.isGlob(key)) {
//            throw new ThingRuntimeException("Key can't be glob for this query");
//        }
//        return getChildrenMatchingTypeAndKey(things, "*", key);
//    }

    abstract Observable<? extends Thing<?>> getChildrenMatchingTypeAndKey(Observable<? extends Thing<?>> things, String typeMatcher, String keyMatcher);


    /**
     * Returns an {@link rx.Observable} of all Things what match the provided type and key.
     * <p>
     * Both type and key can be globs, in which case the lookup might take
     * longer since there might be more than one
     * {@link things.control.ThingReader} configured for a type glob.
     * <p>
     * Another example, to find all Things of a certain type you can use "*" as
     * the key.
     * <p>
     * The return type {@link java.util.stream.Stream} is used to be able to do
     * concurrent lookups for different types at the same time and
     * consolidate/filter the results as they come in. This might change in the
     * future if it turns out a bad idea...
     *
     * @param type the type (or type-glob)
     * @param key  the key (or key-glob)
     * @return a stream of Things that match the provided type and key
     */
    abstract Observable<? extends Thing<?>> findThingsMatchingTypeAndKey(final String type,
                                                           final String key);

    abstract Observable<? extends Thing<?>> findAllThings();

    abstract <V> V readValue(Thing<V> thing);

    abstract  <V> Observable<Thing<V>> findThingsMatchingKeyAndValue(String keyMatcher, V value);

    abstract  <V> Observable<Thing<V>> findThingsForValue(V value);

    abstract  <V> Observable<Thing<V>> getChildrenForValue(Observable<? extends Thing<?>> thing, V value);

    abstract  <V> Observable<Thing<V>> findThingsForValue(Observable<? extends Thing<?>> things, V value);
}
