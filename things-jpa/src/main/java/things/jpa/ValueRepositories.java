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

package things.jpa;

import com.google.common.collect.Maps;
import things.exceptions.TypeRuntimeException;

import java.util.Collection;
import java.util.Map;

/**
 * Created by markus on 20/05/14.
 */
public class ValueRepositories {

    private Map<String, ValueRepository> valueRepositories = Maps.newHashMap();

    public void addRepository(String type, ValueRepository repo) {
        valueRepositories.put(type, repo);
    }

    public ValueRepository get(String type) {
        ValueRepository repo = valueRepositories.get(type);

        if ( repo == null ) {
            throw new TypeRuntimeException("No value repository found for type: " + type);
        }

        return repo;
    }

    public Collection<ValueRepository> getAll() {
        return valueRepositories.values();
    }
}
