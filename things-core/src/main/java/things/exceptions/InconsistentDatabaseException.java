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

package things.exceptions;

import com.google.common.collect.Lists;
import things.thing.Thing;

import java.util.List;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner Date: 31/03/14 Time: 4:40 PM
 */
public class InconsistentDatabaseException extends RuntimeException {

    private final List<Thing> things;

    public InconsistentDatabaseException(String msg) {
        super(msg);
        this.things = Lists.newArrayList();
    }

    public InconsistentDatabaseException(String msg, List<Thing> things) {
        super(msg);
        this.things = things;
    }

    public List<Thing> getThings() {
        return things;
    }
}
