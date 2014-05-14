package things.thing;

import rx.Observable;
import rx.Subscriber;
import things.types.TypeRegistry;
import things.utils.MatcherUtils;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 14/05/14
 * Time: 10:21 PM
 */
abstract public class AbstractThingReader implements ThingReader {

    protected TypeRegistry typeRegistry = null;

    public AbstractThingReader(TypeRegistry typeRegistry) {
        this.typeRegistry = typeRegistry;
    }

    public Observable<? extends Thing<?>> findThingsMatchingType(String typeMatcher) {
        return findThingsMatchingTypeAndKey(typeMatcher, "*");
    }

    public Observable<? extends Thing<?>> findThingsMatchingKey(String keyMatcher) {
        return findThingsMatchingTypeAndKey("*", keyMatcher);
    }

    public Observable<? extends Thing<?>> findThingsForType(String type) {
        return findThingsMatchingTypeAndKey(type, "*");
    }

    public Observable<? extends Thing<?>> findThingsForKey(String key) {

        return findThingsMatchingTypeAndKey("*", key);
    }

    public Observable<? extends Thing<?>> findThingsForTypeAndKey(String type, String key) {
        return findThingsMatchingTypeAndKey(type, key);
    }

    public Observable<? extends Thing<?>> findThingForId(String id) {
        Observable<? extends Thing<?>> allThings = findAllThings();
        return allThings.filter(t -> id.equals(t.getId())).single();
    }

    public Observable<? extends Thing<?>> getChildrenForId(String id) {
        Observable<? extends Thing<?>> obs = findAllThings();
        return obs.filter(t -> t.getParents().contains(id));
    }

    public Observable<? extends Thing<?>> getChildrenMatchingTypeAndKey(Observable<? extends Thing<?>> things, String typeMatcher, String keyMatcher) {

        Observable result = things.flatMap(t -> getChildrenForId(t.getId()))
                .filter(t -> MatcherUtils.wildCardMatch(t.getThingType(), typeMatcher)
                        && MatcherUtils.wildCardMatch(t.getKey(), keyMatcher));

        return result;
    }

    public Observable<? extends Thing<?>> findThingsMatchingTypeAndKey(final String type,
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

    public <V> Observable<Thing<V>> findThingsMatchingKeyAndValue(String keyMatcher, V value) {
        Observable<? extends Thing<?>> obs = findThingsMatchingTypeAndKey(typeRegistry.getType(value), keyMatcher);
        Observable<Thing<V>> result = findThingsForValue(obs, value).map(t -> (Thing<V>)t);
        return result;
    }

    public <V> Observable<Thing<V>> findThingsForValue(V value) {
        Observable<? extends Thing<?>> obs = findThingsForType(typeRegistry.getType(value));
        Observable<Thing<V>> result = findThingsForValue(obs, value).map(t -> (Thing<V>)t);
        return result;
    }

    public <V> Observable<Thing<V>> getChildrenForValue(Observable<? extends Thing<?>> thing, V value) {
        return findThingsForValue(thing.flatMap(t -> getChildrenForId(t.getId())), value);
    }

    public <V> Observable<Thing<V>> findThingsForValue(Observable<? extends Thing<?>> things, V value) {

        return things.filter(t -> readValue(t).equals(value)).map(t -> (Thing<V>)t);
    }

}
