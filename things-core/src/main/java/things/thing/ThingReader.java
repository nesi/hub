package things.thing;


import rx.Observable;
import rx.Subscriber;
import things.exceptions.ThingRuntimeException;
import things.utils.MatcherUtils;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 4/05/14
 * Time: 10:13 PM
 */
public interface ThingReader {

    default Observable<? extends Thing<?>> findThingsMatchingType(String typeMatcher) {
        return findThingsMatchingTypeAndKey(typeMatcher, "*");
    }

    default Observable<? extends Thing<?>> findThingsMatchingKey(String keyMatcher) {
        return findThingsMatchingTypeAndKey("*", keyMatcher);
    }

    default Observable<? extends Thing<?>> findThingsForType(String type) {
        return findThingsMatchingTypeAndKey(type, "*");
    }

    default Observable<? extends Thing<?>> findThingsForKey(String key) {

        return findThingsMatchingTypeAndKey("*", key);
    }

    default Observable<? extends Thing<?>> findThingsForTypeAndKey(String type, String key) {
        return findThingsMatchingTypeAndKey(type, key);
    }

    default Observable<? extends Thing<?>> findThingForId(String id) {
        Observable<? extends Thing<?>> allThings = findAllThings();
        return allThings.filter(t -> id.equals(t.getId())).single();
    }

    default Observable<? extends Thing<?>> getChildrenForId(String id) {
        Observable<? extends Thing<?>> obs = findAllThings();
        return obs.filter(t -> t.getParents().contains(id));
    }

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

    default Observable<? extends Thing<?>> getChildrenMatchingTypeAndKey(Observable<? extends Thing<?>> things, String typeMatcher, String keyMatcher) {

        Observable result = things.flatMap(t -> getChildrenForId(t.getId()))
                .filter(t -> MatcherUtils.wildCardMatch(t.getThingType(), typeMatcher)
                        && MatcherUtils.wildCardMatch(t.getKey(), keyMatcher));

        return result;
    }


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
    default Observable<? extends Thing<?>> findThingsMatchingTypeAndKey(final String type,
                                                           final String key) {

        Observable obs = Observable.create((Subscriber<? super Object> subscriber) -> {

                findAllThings().subscribe(
                        (thing) -> {
                            if (MatcherUtils.wildCardMatch(thing.getThingType(), type)
                                    && MatcherUtils.wildCardMatch(thing.getKey(), key)) {
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

    abstract Observable<? extends Thing<?>> findAllThings();

    abstract <V> V readValue(Thing<V> thing);

    default <V> Observable<Thing<V>> findThingsMatchingKeyAndValue(String keyMatcher, V value) {
        Observable<? extends Thing<?>> obs = findThingsMatchingTypeAndKey(TypeRegistry.getType(value), keyMatcher);
        Observable<Thing<V>> result = findThingsForValue(obs, value).map(t -> (Thing<V>)t);
        return result;
    }

    default <V> Observable<Thing<V>> findThingsForValue(V value) {
        Observable<? extends Thing<?>> obs = findThingsForType(TypeRegistry.getType(value));
        Observable<Thing<V>> result = findThingsForValue(obs, value).map(t -> (Thing<V>)t);
        return result;
    }

    default <V> Observable<Thing<V>> getChildrenForValue(Observable<? extends Thing<?>> thing, V value) {
        return findThingsForValue(thing.flatMap(t -> getChildrenForId(t.getId())), value);
    }

    default <V> Observable<Thing<V>> findThingsForValue(Observable<? extends Thing<?>> things, V value) {

        return things.filter(t -> readValue(t).equals(value)).map(t -> (Thing<V>)t);
    }
}
