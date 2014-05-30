package things.thing;

import rx.Observable;

/**
 * Project: things-to-build
 *
 * Written by: Markus Binsteiner
 * Date: 4/05/14
 * Time: 10:13 PM
 */
public interface ThingReader {

    abstract Observable<? extends Thing<?>> findAllThings();

    abstract Observable<? extends Thing<?>> findThingForId(String id);

    abstract Observable<? extends Thing<?>> findThingsForKey(String key);

    abstract <V> Observable<Thing<V>> findThingsForKeyAndValue(String key, V value);

    abstract Observable<? extends Thing<?>> findThingsForType(String type);

    abstract Observable<? extends Thing<?>> findThingsForTypeAndKey(String type, String key);

    abstract <V> Observable<Thing<V>> findThingsForValue(V value);

    abstract <V> Observable<Thing<V>> findThingsForValue(Observable<? extends Thing<?>> things, V value);

    abstract Observable<? extends Thing<?>> findThingsMatchingKey(String keyMatcher);

    abstract <V> Observable<Thing<V>> findThingsMatchingKeyAndValue(String keyMatcher, V value);

    abstract Observable<? extends Thing<?>> findThingsMatchingType(String typeMatcher);

    abstract public Observable<? extends Thing<?>> findThingsForTypeMatchingKey(String type, String key);


    /**
     * Returns an {@link rx.Observable} of all Things what match the provided type and key.
     *
     * Both type and key can be globs, in which case the lookup might take
     * longer since there might be more than one
     * {@link things.thing.ThingReader} configured for a type glob.
     *
     * Another example, to find all Things of a certain type you can use "*" as
     * the key.
     *
     * @param type the type (or type-glob)
     * @param key  the key (or key-glob)
     * @return an observable of Things that match the provided type and key
     */
    abstract Observable<? extends Thing<?>> findThingsMatchingTypeAndKey(final String type,
                                                                         final String key);

    abstract Observable<? extends Thing<?>> getChildrenForId(String id);

    abstract Observable<? extends Thing<?>> getChildrenForKey(Observable<? extends Thing<?>> things, String type);

    abstract Observable<? extends Thing<?>> getChildrenForType(Observable<? extends Thing<?>> things, String type);

    abstract Observable<? extends Thing<?>> getChildrenForTypeAndKey(Observable<? extends Thing<?>> things, String type, String key);

    abstract <V> Observable<Thing<V>> getChildrenForValue(Observable<? extends Thing<?>> thing, V value);

    abstract Observable<? extends Thing<?>> getChildrenMatchingKey(Observable<? extends Thing<?>> things, String keyMatcher);

    abstract Observable<? extends Thing<?>> getChildrenMatchingType(Observable<? extends Thing<?>> things, String typeMatcher);

    abstract Observable<? extends Thing<?>> getChildrenMatchingTypeAndKey(Observable<? extends Thing<?>> things, String typeMatcher, String keyMatcher);

    abstract <V> V readValue(Thing<V> thing);
}
