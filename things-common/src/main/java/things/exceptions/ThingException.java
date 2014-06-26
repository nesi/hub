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


import things.thing.Thing;

/**
 * Project: researchHub
 * <p>
 * Written by: Markus Binsteiner
 * Date: 14/01/14
 * Time: 4:19 PM
 */
public class ThingException extends Exception {

    private final Thing p;

    public ThingException() {
        super();
        this.p = null;
    }

    public ThingException(String msg) {
        super(msg);
        this.p = null;
    }

    public ThingException(Thing p, String msg) {
        super(msg);
        this.p = p;
    }

    public ThingException(String s, Exception e) {
        super(s, e);
        this.p = null;
    }

    public ThingException(Thing p, String msg, Exception e) {
        super(msg, e);
        this.p = p;
    }


    public Thing getThing() {
        return p;
    }
}
