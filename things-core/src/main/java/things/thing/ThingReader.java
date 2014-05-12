package things.thing;


import rx.Observable;
import rx.Subscriber;
import things.exceptions.ThingRuntimeException;
import things.exceptions.TypeRuntimeException;
import things.utils.MatcherUtils;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 4/05/14
 * Time: 10:13 PM
 */
public interface ThingReader {

    abstract String getReaderName();

    default Observable<Thing> findThingsMatchingType(String typeMatcher) {
        return findThingsMatchingTypeAndKey(typeMatcher, "*");
    }

    default Observable<Thing> findThingsMatchingKey(String keyMatcher) {
        return findThingsMatchingTypeAndKey("*", keyMatcher);
    }

    default Observable<Thing> findThingsForType(String type) {
        if (MatcherUtils.isGlob(type)) {
            throw new TypeRuntimeException("Type can't be glob when using the find for type method", type);
        }
        return findThingsMatchingTypeAndKey(type, "*");
    }

    default Observable<Thing> findThingsForKey(String key) {
        if (MatcherUtils.isGlob(key)) {
            throw new TypeRuntimeException("Type can't be glob when using the find for key method", key);
        }
        return findThingsMatchingTypeAndKey("*", key);
    }

    default Observable<Thing> findThingsForTypeAndKey(String type, String key) {
        if (MatcherUtils.isGlob(type)) {
            throw new TypeRuntimeException("Type can't be glob when using the find for type method", type);
        }
        if (MatcherUtils.isGlob(key)) {
            throw new TypeRuntimeException("Type can't be glob when using the find for key method", key);
        }
        return findThingsMatchingTypeAndKey(type, key);
    }

    default Observable<Thing> findThingForId(String id) {
        Observable<Thing> allThings = findAllThings();
        return allThings.filter(t -> id.equals(t.getId())).single();
    }

    default Observable<Thing> getChildrenForId(String id) {
        Observable<Thing> obs = findAllThings();
        return obs.filter(t -> t.getParents().contains(id));
    }

    default Observable<Thing> getChildrenMatchingType(Thing thing, String typeMatcher) {
        return getChildrenMatchingTypeAndKey(thing, typeMatcher, "*");
    }

    default Observable<Thing> getChildrenForType(Thing thing, String type) {
        if ( MatcherUtils.isGlob(type)) {
            throw new ThingRuntimeException("Type can't be glob for this query");
        }
        return getChildrenMatchingTypeAndKey(thing, type, "*");
    }

    default Observable<Thing> getChildrenMatchingKey(Thing thing, String keyMatcher) {
        return getChildrenMatchingTypeAndKey(thing, "*", keyMatcher);
    }

    default Observable<Thing> getChildrenForKey(Thing thing, String key) {
        if (MatcherUtils.isGlob(key)) {
            throw new ThingRuntimeException("Key can't be glob for this query");
        }
        return getChildrenMatchingTypeAndKey(thing, "*", key);
    }

    default Observable<Thing> getChildrenForTypeAndKey(Thing thing, String type, String key) {
        if (MatcherUtils.isGlob(type)) {
            throw new ThingRuntimeException("Type can't be glob for this query");
        }
        if (MatcherUtils.isGlob(key)) {
            throw new ThingRuntimeException("Key can't be glob for this query");
        }
        return getChildrenMatchingTypeAndKey(thing, "*", key);
    }

    default Observable<Thing> getChildrenMatchingTypeAndKey(Thing thing, String typeMatcher, String keyMatcher) {
        Observable<Thing> allThingsMatchingTypeAndKey = findThingsMatchingTypeAndKey(typeMatcher, keyMatcher);
        return allThingsMatchingTypeAndKey.filter(t -> t.getParents().contains(thing.getId()));
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
    default Observable<Thing> findThingsMatchingTypeAndKey(final String type,
                                                           final String key) {

        Observable obs = Observable.create((Subscriber<? super Object> subscriber) -> {

                findAllThings().subscribe(
                        (thing) -> {
                            if (MatcherUtils.wildCardMatch(thing.getThingType(), type)
                                    && MatcherUtils.wildCardMatch(thing.getKey(), key)) {
                                subscriber.onNext(thing);
                            }
                        },
                        (throwable) -> subscriber.onError(throwable),
                        () -> subscriber.onCompleted()
                );
        });
        return obs;
    }

    abstract Observable<Thing> findAllThings();

    abstract <V> V readValue(Thing<V> thing);

    abstract <V> Observable<Thing<V>> findThingsByKeyAndValue(String key, V value);
}
