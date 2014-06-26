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
 * Project: things
 * <p>
 * Written by: Markus Binsteiner Date: 31/03/14 Time: 4:10 PM
 */
public class TypeRuntimeException extends RuntimeException {

    private final String type;
    private final Class typeClass;

    public TypeRuntimeException(String msg) {
        super(msg);
        this.typeClass = null;
        this.type = null;
    }


    public TypeRuntimeException(String msg, Class typeClass) {
        super(msg);
        this.typeClass = typeClass;
        this.type = null;
    }

    public TypeRuntimeException(String msg, Class typeClass, Exception e) {
        super(msg, e);
        this.typeClass = typeClass;
        this.type = null;
    }

    public TypeRuntimeException(String msg, String type) {
        super(msg);
        this.type = type;
        this.typeClass = null;
    }

    public TypeRuntimeException(String msg, String type, Exception e) {
        super(msg, e);
        this.type = type;
        this.typeClass = null;
    }

    public String getType() {
        return type;
    }
}
