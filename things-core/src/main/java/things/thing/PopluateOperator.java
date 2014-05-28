package things.thing;

import rx.Observable;
import rx.Subscriber;

/**
 * Convenience class to be able to populate a {@link Thing} as part of an Observable chain.
 */
public final class PopluateOperator<T> implements Observable.Operator<T, T> {

    private final ThingControlMinimal tc;

    public PopluateOperator(ThingControlMinimal tc) {
        this.tc = tc;
    }

    @Override
    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        return new Subscriber<T>() {
            @Override
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }

            @Override
            public void onNext(T t) {
                subscriber.onNext(populate(t));

            }
        };
    }

    /**
     * Ensures that the provided Thing has it's value ready.
     */
    public T populate(T thing) {
        Thing<? super Thing> t = (Thing<? super Thing>) thing;
        tc.getValue(t);
        return thing;
    }
}
