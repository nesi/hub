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

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import things.exceptions.NoSuchTypeException;
import things.thing.Thing;

import java.util.*;

/**
 * Project: things
 *
 * Written by: Markus Binsteiner
 * Date: 14/05/14
 * Time: 8:43 PM
 */
public class TypeRegistry {

    private final Map<Class, ThingType<?>> typeClasses = Maps.newHashMap();
    private final TreeMap<String, ThingType<?>> types = Maps.newTreeMap();

    public TypeRegistry() {
    }

    public TypeRegistry(Collection<? extends ThingType<?>> types) {
        Preconditions.checkArgument(types != null, "Types can't be null");
        for ( ThingType<?> type : types ) {
            addType(type);
        }
    }

    public void addType(ThingType<?> type) {
        Preconditions.checkArgument(type != null, "Type can't be null");
        types.put(type.getType(), type);
        typeClasses.put(type.getTypeClass(), type);
    }

    public Optional<? extends Object> convertFromString(Class typeClass, String valueString) {
        Preconditions.checkArgument(typeClass != null, "Type can't be null");
        Preconditions.checkArgument(valueString != null, "Value string can't be null");
        return getThingType(typeClass).convertFromString(valueString);
    }

    public Optional<? extends Object> convertFromString(String type, String valueString) {
        Preconditions.checkArgument(type != null, "Type can't be null");
        Preconditions.checkArgument(valueString != null, "Value string can't be null");
        return getThingType(type).convertFromString(valueString);
    }

    public Optional<String> convertToString(Object value) {
        Preconditions.checkArgument(value != null, "Value can't be null");
        return getThingType(value).convertToString(value);

    }

    public boolean convertsFromString(String thingType) {
        Preconditions.checkArgument(thingType != null, "Type can't be null");
        return getThingType(thingType).convertsFromString();
    }

    public boolean convertsFromString(Class typeClass) {
        Preconditions.checkArgument(typeClass != null, "Type can't be null");
        return getThingType(typeClass).convertsFromString();
    }

    public Boolean equals(Class typeClass, String thingType) {
        return getThingType(typeClass).equals(getThingType(thingType));
    }

    public Boolean equals(String thingType, Class typeClass) {
        return getThingType(typeClass).equals(getThingType(thingType));
    }

    public Boolean equals(String type, Object value) {
        return getThingType(type).equals(getThingType(value));
    }

    public Boolean equals(Object value, String type) {
        return getThingType(type).equals(getThingType(value));
    }

    public Boolean equals(Class typeClass, Object value) {
        return getThingType(typeClass).equals(getThingType(value));
    }

    public Boolean equals(Object value, Class typeClass) {
        return getThingType(typeClass).equals(getThingType(value));
    }

    public Collection<ThingType<?>> getAllThingTypes() {
        return types.values();
    }

    public Set<String> getAllTypes() {
        return types.keySet();
    }

    public ThingType getThingType(Class<?> type) {
        Preconditions.checkArgument(type != null, "Type can't be null");
        if ( typeClasses.get(type) != null ) {
            return typeClasses.get(type);
        } else {
            throw new NoSuchTypeException("Can't find typeClass for class: " + type.toString());
        }
    }

    public ThingType getThingType(String type) {
        Preconditions.checkArgument(! Strings.isNullOrEmpty(type), "Type can't be null or empty");
        if ( types.get(type) != null ) {
            return types.get(type);
        } else {
            throw new NoSuchTypeException("Can't find typeClass: " + type);
        }
    }

    public ThingType getThingType(Object value) {
        if ( value instanceof Thing ) {
            return getThingType(((Thing) value).getThingType());
        } else {
            return getThingType(value.getClass());
        }
    }

    public String getType(Class<?> typeClass) {
        return getThingType(typeClass).getType();
    }

    public String getType(Object value) {
        return getThingType(value).getType();
    }

    public Class<?> getTypeClass(String type) {
        return getThingType(type).getTypeClass();
    }

    public Class<?> getTypeClass(Object value) {
        return getThingType(value).getTypeClass();
    }

    public boolean needsUniqueKey(Object value) {
        return getThingType(value).isNeedsUniqueKey();
    }

    public boolean needsUniqueKey(Class typeClass) {
        return getThingType(typeClass).isNeedsUniqueKey();
    }

    public boolean needsUniqueKey(String type) {
        return getThingType(type).isNeedsUniqueKey();
    }

    public boolean needsUniqueKeyAndValueAsChild(Object value) {
        return getThingType(value).isNeedsUniqueValueForKeyAsChild();
    }

    public boolean needsUniqueKeyAndValueAsChild(Class typeClass) {
        return getThingType(typeClass).isNeedsUniqueValueForKeyAsChild();
    }

    public boolean needsUniqueKeyAndValueAsChild(String type) {
        return getThingType(type).isNeedsUniqueValueForKeyAsChild();
    }

    public boolean needsUniqueKeyAsChild(Object value) {
        return getThingType(value).isNeedsUniqueKeyAsChild();
    }

    public boolean needsUniqueKeyAsChild(Class typeClass) {
        return getThingType(typeClass).isNeedsUniqueKeyAsChild();
    }

    public boolean needsUniqueKeyAsChild(String type) {
        return getThingType(type).isNeedsUniqueKeyAsChild();
    }

    public boolean needsUniqueValueForKey(Object value) {
        return getThingType(value).isNeedsUniqueValue();
    }

    public boolean needsUniqueValueForKey(Class typeClass) {
        return getThingType(typeClass).isNeedsUniqueValue();
    }

    public boolean needsUniqueValueForKey(String type) {
        return getThingType(type).isNeedsUniqueValue();
    }
}
