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

import com.google.common.collect.Sets;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * One of the central classes of this framework. It describes a type, it's name and implementing class,
 * along with some properties like whether an object of this type needs to have a unique key across
 * all of it's sister-objects (of the same type).
 * <p>
 * ThingTypes are managed in the {@link things.types.TypeRegistry}, where new types can be added via {@link things.types.TypeRegistry#addType(ThingType)}.
 * A default factory for gathering types is {@link things.types.AnnotationTypeFactory}, which uses annotations to find
 * all types. But you can implement that anyway you like.
 */
public class ThingType<V> {

    private Set<String> allowedParentTypes = Sets.newHashSet();
    private SingleStringConverter<V> converter;
    private boolean needsParent = false;
    private boolean needsUniqueKey = false;
    private boolean needsUniqueKeyAsChild = false;
    private boolean needsUniqueValue = false;
    private boolean needsUniqueValueForKeyAsChild = false;
    private final String type;
    private final Class typeClass;

    /**
     * Default, and only constructor. Generates a ThingType of type and class.
     * <p>
     * Everything else is left default (i.e. all needs* properties are 'false')
     *
     * @param type      the type name
     * @param typeClass the class that implements this thing-type
     */
    public ThingType(String type, Class typeClass) {
        this.type = type;
        this.typeClass = typeClass;
    }

    /**
     * If this type is able to be build from String ({@link #convertsFromString()},
     * a new object is created from the input string. Otherwise an empty Optional is returned.
     */
    public Optional<V> convertFromString(String valueString) {
        if ( converter != null ) {
            return Optional.of(converter.convertFromString(valueString));
        } else {
            return Optional.empty();
        }
    }

    /**
     * If this type is able to serialize into a String ({@link #convertsFromString()} ()},
     * it is done here. Otherwise an empty Optional is returned.
     */
    public Optional<String> convertToString(V value) {
        if ( converter != null ) {
            return Optional.of(converter.convertToString(value));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Whether this type can be constructed from a String (true) or not (false).
     */
    public boolean convertsFromString() {
        return converter != null;
    }

    @Override
    public boolean equals(Object o) {
        if ( o instanceof ThingType ) {
            return Objects.equals(getType(), ((ThingType) o).getType());
        } else {
            return false;
        }
    }

    /**
     * Returns all types this type can have as a parent.
     */
    public Set<String> getAllowedParentTypes() {
        return allowedParentTypes;
    }

    /**
     * Returns a converter that can convert this type into/from a String,
     * or null.
     */
    private SingleStringConverter<V> getConverter() {
        return converter;
    }

    /**
     * The name of this type.
     */
    public String getType() {
        return type;
    }

    /**
     * The implementing class of this type.
     */
    public Class getTypeClass() {
        return typeClass;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getType());
    }

    /**
     * Returns whether this type needs a parent and can't be added by itself (true) or not (false -- default).
     *
     * Not used at the moment.
     */
    public boolean isNeedsParent() {
        return needsParent;
    }

    /**
     * Returns whether a Thing of this type needs to have a unique key among it's sister-things of the same type.
     */
    public boolean isNeedsUniqueKey() {
        return needsUniqueKey;
    }

    /**
     * Returns whether a Thing of this type needs to have a unique key among it's sister-things of the same type when all are a child of the same parent.
     */
    public boolean isNeedsUniqueKeyAsChild() {
        return needsUniqueKeyAsChild;
    }

    /**
     * Returns whether things of this type need to all have unqiue/different values application-wide.
     */
    public boolean isNeedsUniqueValue() {
        return needsUniqueValue;
    }

    /**
     * Returns whether a Thing of this type needs to have a unique key/value combination among it's sister-things of the same type when all are a child of the same parent.
     */
    public boolean isNeedsUniqueValueForKeyAsChild() {
        return needsUniqueValueForKeyAsChild;
    }

    /**
     * Sets the allowed parent types for this type.
     */
    public void setAllowedParentTypes(Set<String> allowedParentTypes) {
        this.allowedParentTypes = allowedParentTypes;
    }

    /**
     * Sets a converter from/to String for this type.
     */
    public void setConverter(SingleStringConverter<V> converter) {
        this.converter = converter;
    }

    /**
     * Sets whether this type can't stand on it's own and can only exist as child of another thing.
     *
     * (not used at the moment)
     */
    public void setNeedsParent(boolean needsParent) {
        this.needsParent = needsParent;
    }

    /**
     * Sets whether a Thing of this type needs to have a unique key among it's sister-things of the same type.
     *
     * @param needsUniqueKey
     */
    public void setNeedsUniqueKey(boolean needsUniqueKey) {
        this.needsUniqueKey = needsUniqueKey;
    }

    /**
     * Sets whether a Thing of this type needs to have a unique key among it's sister-things of the same type when all are a child of the same parent.
     */
    public void setNeedsUniqueKeyAsChild(boolean needsUniqueKeyAsChild) {
        this.needsUniqueKeyAsChild = needsUniqueKeyAsChild;
    }

    /**
     * Sets whether things of this type need to all have unqiue/different values application-wide.
     */
    public void setNeedsUniqueValue(boolean needsUniqueValue) {
        this.needsUniqueValue = needsUniqueValue;
    }

    /**
     * Sets whether a Thing of this type needs to have a unique key/value combination among it's sister-things of the same type when all are a child of the same parent.
     */
    public void setNeedsUniqueValueForKeyAsChild(boolean needsUniqueValueForKeyAsChild) {
        this.needsUniqueValueForKeyAsChild = needsUniqueValueForKeyAsChild;
    }
}
