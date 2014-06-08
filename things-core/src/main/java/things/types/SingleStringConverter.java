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

package things.types;

/**
 * A SingleStringConverter can convert a type ({@link things.types.ThingType}) into a String, and back.
 */
public interface SingleStringConverter<V> {

    /**
     * Convert the String into a value.
     */
    public V convertFromString(String valueString);

    /**
     * Convert an instance of a value into a String.
     */
    public String convertToString(V value);
}
