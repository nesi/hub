package things.thing;

import rx.Observable;
import rx.Subscriber;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 12/05/14
 * Time: 10:49 PM
 */
public class ConvertToTypedOperator<T,TV> implements Observable.Operator<TV, T> {

    @Override
    public Subscriber<? super T> call(Subscriber<? super TV> subscriber) {
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
                subscriber.onNext((TV)t);
            }
        };
    }
}
