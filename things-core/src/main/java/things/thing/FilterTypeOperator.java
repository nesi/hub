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
public final class FilterTypeOperator<T> implements Observable.Operator<T, T> {

    private final Class filterClass;

    public FilterTypeOperator(Class filterClass) {
        this.filterClass = filterClass;
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
                if ( t.getClass().equals(filterClass) ) {
                    subscriber.onNext(t);
                }
            }
        };
    }
}
