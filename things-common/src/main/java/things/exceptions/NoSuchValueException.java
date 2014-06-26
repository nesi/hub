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

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 4/05/14
 * Time: 10:05 PM
 */
public class NoSuchValueException extends RuntimeException {

    private final String id;
    private final String key;
    private final String type;

    public NoSuchValueException(String id) {
        super("Can't find Thing for id: " + id);
        this.key = null;
        this.type = null;
        this.id = id;
    }

    public NoSuchValueException(String msg, String type, String key, String id) {
        super(msg);
        this.type = type;
        this.key = key;
        this.id = id;
    }

    public NoSuchValueException(String type, String key) {
        super("Can't find thing for type '" + type + "' and key '" + key + "'");
        this.type = type;
        this.key = key;
        this.id = null;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

}
