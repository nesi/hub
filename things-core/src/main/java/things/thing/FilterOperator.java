/*
 * Things
 *
 * Copyright (c) 2014, Markus Binsteiner. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */

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
