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
import hub.actions.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;
import things.exceptions.QueryRuntimeException;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingQuery;
import things.types.TypeRegistry;

import java.util.Map;
import java.util.Set;

/**
 * Created by markus on 19/05/14.
 */
public class UserQuery implements ThingQuery {

    @Autowired
    private ThingControl tc;
    @Autowired
    private TypeRegistry typeRegistry;
    @Autowired
    private UserUtils userUtils;

    @Override
    public Observable<? extends Thing<?>> execute(String queryName, Observable<? extends Thing<?>> things, Map<String, String> parameters) {

        switch ( queryName ) {
            case "details":
                return userUtils.convertToPerson(things).map(t -> userUtils.createUser(t));
            default:
                throw new QueryRuntimeException("Can't find query with name: " + queryName);
        }

    }

    @Override
    public Set<String> getSupportedQueryNames() {
        return ImmutableSet.<String>builder().add("details").build();
    }
}
