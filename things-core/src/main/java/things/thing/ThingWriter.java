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

import java.util.Optional;

/**
 * A ThingWriter is a backend component that is able to persist things.
 *
 * In most cases this will be a connector to a database, or filesystem storage of some sort.
 */
public interface ThingWriter {

    /**
     * Adds a parent-child relationship for the two provided objects.
     *
     * Internally, a parent reference should be stored on the child object, not the other way around. Since this
     * particular writer might not have the ability to ensure the persistence of the parent thing.
     *
     * @param parent the parent
     * @param child  the child
     * @return the child (most likely the same object as the input-child, but can't be guaranteed
     */
    abstract <V> Thing<V> addChild(Thing<?> parent, Thing<V> child);

    /**
     * Delete the thing with the specified id.
     *
     * The Type and Key arguments are optional. They are used to speed up finding the appropriate
     * ThingWriter (using {@link things.config.ThingWriters}), without having to invoke all of them.
     *
     * @param id   the id of the thing
     * @param type the (optional) type
     * @param key  the (optional) key
     * @return whether the thing was successfully deleted (true) or not (false)
     */
    abstract boolean deleteThing(String id, Optional<String> type, Optional<String> key);

    /**
     * Persists this thing.
     *
     * @param t the thing
     * @return the reference to the persisted thing. It is advised to always use that from the moment of calling this method, it can't be guaranteed that the old thing is not obsolete
     */
    abstract <V> Thing<V> saveThing(Thing<V> t);

}
