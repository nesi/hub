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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import things.types.TypeRegistry;

import java.util.Objects;
import java.util.Set;

/**
 * A *Thing* is the central piece in this framework. In a nutshell, it wraps a value and makes it
 * accessible via a type & key combination.
 *
 * The main elements of a Thing are:
 *
 * - __id__: id, unique across all Things
 * - __thingType__: the type of the value (called 'thingType' instead of simply 'type' because 'type' seems to be a keyword way too often to not cause problems
 * - __key__: the key that can be used to access this value. Keys don't need to be unique, even among Things with the same type
 * - __value__: the value that is wrapped by this Thing
 * - __parent__: a list of ids that point to other things that this thing is a child of
 *
 * ### Properties of a Thing
 *
 * ##### id
 *
 * When creating a Thing, you should not set an id, those are usually set by the underlying storage backend ({@link things.thing.ThingWriter}).
 *
 * ##### thingType
 *
 * The type of a Thing is linked to the implementing class of the @see Value it wrappes. You can use a {@link things.types.TypeRegistry}
 * to convert between Type-strings, Type-classes and Value-types, there are also convenience methods to compare and inspect types. Further information in the {@link things.types.ThingType} javadoc.
 *
 * Types are needed for serializing and de-serializing values, both in a Thing-API and underlying storage engine.
 *
 * ##### key
 *
 * Keys are used for look-ups. A Thing can have either:
 *
 * - a unique key for all Things of a certain type (indicated by the {@link things.model.types.attributes.UniqueKey} annotation), Things of a different type will still be able to use the same key
 * - a unique key within all childs of a certain Type of a Thing (indicated by the {@link things.model.types.attributes.UniqueKeyAsChild} annotation), Things of a different type will still be able to use the same key
 * - a key that is used by other Things of the same or other types
 *
 * ##### value
 *
 * A value can implement any Java class (preferrably a POJO with id value though)
 *
 * Every storage backend ({@link things.thing.ThingWriter}) can implement it's own way of storing such a value.
 * Normally a value would be stored and retrieved via an 'id' field.
 * When looking up the value of Thing, always do via the {@link things.thing.ThingControl#getValue(things.thing.Thing)} method,
 * because it is not guaranteed that the value is populated (lazy loading), so the {@link #getValue()} method may return null.
 *
 * If the Value type is so that it only contains one String, it can be marked by the {@link things.model.types.attributes.StringConverter} attribute. If that is present,
 * a {@link things.thing.ThingWriter} can decide to serialize the value and, for example, store it directly as a property of a Thing, which might make querying more effective.
 *
 * ##### parents
 *
 * Things can have links to other Things, which enables (simple) modelling of relationships. If a Thing can only be added to a certain Type of
 * other Thing, you can specify that via the {@link things.model.types.attributes.Subordinate} annotation.
 *
 * @author Markus Binsteiner
 */
public class Thing<V> implements Comparable<Thing>, java.io.Serializable {

    public static <T> Thing<T> createThingPoJo(TypeRegistry tr, String key, T value) {

        Thing<T> t = new Thing();
        t.setThingType(tr.getType(value.getClass()));
        t.setValue(value);
        t.setValueIsPopulated(true);
        t.setKey(key);

        return t;
    }

    private String id;

    private String key;

    private Set<String> parents = Sets.newHashSet();
    private String thingType;
    protected V value;
    private Boolean valueIsPopulated = true;

    /**
     * Default constructor, don't use in normal circumstances.
     */
    public Thing() {
    }

    public Thing(String type, String key) {
        this.thingType = type;
        this.key = key;
    }

    @Override
    public int compareTo(Thing o) {
        return ComparisonChain.start().compare(getThingType(), o.getThingType())
                .compare(getKey(), o.getKey())
                        //.compare(getValue(), o.getValue(), valueComparator)
                        //.compare(getValue(), o.getValue(), Ordering.natural().nullsFirst())
                .compare(getId(), o.getId(), Ordering.natural().nullsFirst())
                .result();
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == this ) return true;
        if ( obj == null ) return false;

        if ( getClass().equals(obj.getClass()) ) {
            final Thing other = (Thing) obj;
            if ( getId() == null && other.getId() == null ) {
                return Objects.equals(getThingType(), other.getThingType()) && Objects.equals(getValue(), other.getValue());
            } else {
                return Objects.equals(getId(), other.getId());
            }
        } else {
            return false;
        }
    }

    /**
     * Returns the id of this thing.
     *
     * Ids are created by the underlying storage backend and unique among all Things.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the key of this thing.
     */
    public String getKey() {
        return key;
    }

    // --------------------- GETTER / SETTER METHODS ---------------------

    /**
     * Returns all the parent references of this thing
     */
    public Set<String> getParents() {
        return parents;
    }

    /**
     * Returns the type (see: {@link things.types.ThingType}) of this Things' value.
     */
    public String getThingType() {
        return thingType;
    }

    /**
     * The value that is wrapped by this thing.
     *
     * Try to avoid getting it directly, instead use {@link things.thing.ThingControl#getValue(things.thing.Thing)} to make sure the value is populated by the associated {@link things.thing.ThingReader}.
     * If you can't do that for some reason, make sure you check the {@link #getValueIsPopulated()} method before retrieving the value.
     */
    public V getValue() {
        return value;
    }

    /**
     * Returns the state of this thing, i.e. whether the associated {@link things.thing.ThingReader} did populate this Thing with the actual value (true) or whether lazy loading is used and the value is not populated (false).
     */
    public Boolean getValueIsPopulated() {
        return valueIsPopulated;
    }

    @Override
    public int hashCode() {
        if ( getId() == null ) {
            return Objects.hash(getValue(), getThingType());
        }
        return Objects.hashCode(getId());
    }

    /**
     * Used internally for serialization/deserialization. Don't use if you don't know what you are doing.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the key of this thing.
     *
     * Usually you should not need to have to use this method.
     *
     * @param key the key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Sets this Things parents.
     *
     * You should not need to use this method, if you want to add Things to Things to be stored
     * on the storage backend use {@link ThingControl#addChildThing(things.thing.Thing, things.thing.Thing)}.
     *
     * When returning Things via the API, parents are not included in the serialization, so don't
     * waste cycles adding Things in the first place.
     *
     * @param ids the list of ids that point to this Things' other Things
     */
    public void setParents(Set<String> ids) {
        this.parents = ids;
    }

    /**
     * Sets this Things' type. Make sure you set a valid type (for example, check the {@link things.types.TypeRegistry} that is used in your application.
     */
    public void setThingType(String type) {
        thingType = type;
    }

    /**
     * Sets the value of this Thing.
     *
     * It is preferrable to create a thing via {@link things.thing.ThingControl#createThing(String, Object)}, not doing it manually.
     * If you use this method within a {@link things.thing.ThingReader}/{@link things.thing.ThingWriter}, make sure you also set the correct via
     * {@link #setThingType(String)} and also the populated state of this thing using {@link #setValueIsPopulated(Boolean)}.
     *
     * @param value the value
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Sets whether this things value is the actual value (true), or whether it has to be loaded still (lazy loading - false).
     */
    public void setValueIsPopulated(Boolean valueIsPopulated) {
        this.valueIsPopulated = valueIsPopulated;
    }

    @Override
    public String toString() {
        return "Thing{" +
                "id='" + id + '\'' +
                ", type='" + getThingType() +
                "', key='" + key + '\'' +
                ", value='" + value +
                "', parents=" + parents +
                '}';
    }
}
