package things.thing;

import com.google.common.collect.Maps;
import rx.Observable;
import rx.Subscriber;
import things.types.TypeRegistry;

import java.util.Map;

/**
 * Convenience class to be able to populate a {@link things.thing.Thing} as part of an Observable chain.
 */
public final class FilterOperator<TYPE, R extends Thing<TYPE>, I extends Thing<?>> implements Observable.Operator<R, I> {

    public final static Map<Class, FilterOperator> filterMap = Maps.newHashMap();

    public final static synchronized <TYPE> Observable.Operator<Thing<TYPE>, Thing<?>> get(Class<TYPE> typeClass, TypeRegistry tr) {

        if ( filterMap.get(typeClass) == null ) {
            FilterOperator fo = new FilterOperator(typeClass, tr);
            filterMap.put(typeClass, fo);
        }

        return filterMap.get(typeClass);
    }

    private final TypeRegistry tr;
    private final Class<TYPE> filterType;

    public FilterOperator(Class<TYPE> filterType, TypeRegistry tr) {
        this.tr = tr;
        this.filterType = filterType;
    }


    @Override
    public Subscriber<? super I> call(Subscriber<? super R> subscriber) {
        return new Subscriber<I>() {
            @Override
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }

            @Override
            public void onNext(I r) {
                subscriber.onNext((R)r);
            }
        };
    }
}
