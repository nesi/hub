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
