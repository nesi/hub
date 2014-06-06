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

package hub.queries.users;

import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingQuery;

import java.util.Map;
import java.util.Set;

/**
 * Created by markus on 28/05/14.
 */
public class GroupQuery implements ThingQuery {

    @Autowired
    private ThingControl tc;

    @Override
    public Observable<? extends Thing<?>> execute(String queryName, Observable<? extends Thing<?>> things, Map<String, String> parameters) {
        return null;
    }

    @Override
    public Set<String> getSupportedQueryNames() {
        return ImmutableSet.<String>of("users");
    }
}
