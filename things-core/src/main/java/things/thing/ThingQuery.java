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

import java.util.Map;
import java.util.Set;

/**
 * A ThingQuery is a query that is a tad more free-style than the default ones that can be executed
 * via {@link things.thing.ThingReader}s and {@link things.thing.ThingControl}.
 *
 * It can (but does not need to) use the incoming Thing-Observables as query parameters. Queries should not take
 * too long in general, a client should be able to wait for the result without having to worry about timeouts.
 * If that is not possible, a {@link things.thing.ThingAction} might be better suited.
 */
public interface ThingQuery {

    /**
     * The method that is called to start the Query.
     *
     * @param queryName  the name under which this query is called
     * @param things     (optional) Things to use in this query as input or parameter
     * @param parameters additional query parameters (for finetuning, filtering, etc...)
     * @return the result of the query
     */
    public Observable<? extends Thing<?>> execute(String queryName, Observable<? extends Thing<?>> things, Map<String, String> parameters);

    /**
     * A list of Strings that show which query commands this Class supports.
     */
    public Set<String> getSupportedQueryNames();
}
