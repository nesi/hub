package things.thing;

import rx.Observable;
import rx.Subscriber;
import things.utils.MatcherUtils;

/**
 * Created by markus on 20/05/14.
 */
public abstract class AbstractSimpleThingReader extends AbstractThingReader {

    public Observable<? extends Thing<?>> findThingForId(String id) {

        Observable<? extends Thing<?>> allThings = findAllThings();
        return allThings.filter(t -> id.equals(t.getId()));
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
}
