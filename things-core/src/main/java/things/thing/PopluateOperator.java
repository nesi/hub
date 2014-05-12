package things.thing;

import rx.Observable;
import rx.Subscriber;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 12/05/14
 * Time: 9:14 PM
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

    public T populate(T thing) {
        Thing<? super Thing> t = (Thing<? super Thing>) thing;
        return (T)tc.ensurePopulatedValue(t);
    }
}
