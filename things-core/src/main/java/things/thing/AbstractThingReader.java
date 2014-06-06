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

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.google.common.collect.Maps;
import rx.Observable;
import things.exceptions.ThingRuntimeException;
import things.types.TypeRegistry;
import things.utils.MatcherUtils;

import javax.inject.Inject;
import java.util.Map;
import static com.codahale.metrics.MetricRegistry.name;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 14/05/14
 * Time: 10:21 PM
 */
abstract public class AbstractThingReader implements ThingReader {

    protected TypeRegistry typeRegistry = null;

    abstract public Observable<? extends Thing<?>> findThingForId(String id);

    abstract public Observable<? extends Thing<?>> findThingsMatchingTypeAndKey(final String type,
                                                                                final String key);

    abstract public Observable<? extends Thing<?>> getChildrenForId(String id);

    abstract public Observable<? extends Thing<?>> getChildrenMatchingTypeAndKey(Observable<? extends Thing<?>> things, String typeMatcher, String keyMatcher);

    abstract public Observable<? extends Thing<?>> findThingsForTypeAndKey(String type, String key);

    abstract public Observable<? extends Thing<?>> findThingsForTypeMatchingKey(String type, String key);

    protected boolean equalsValue(Thing<?> t, Object value) {

        Object thingValue = null;
        if ( !t.getValueIsPopulated() ) {
            thingValue = readValue(t);
        } else {
            thingValue = t.getValue();
        }

        return thingValue.equals(value);
    }

    public Observable<? extends Thing<?>> findThingsForKey(String key) {

            return findThingsMatchingTypeAndKey("*", key);

    }

    public <V> Observable<Thing<V>> findThingsForKeyAndValue(String key, V value) {

            Observable<? extends Thing<?>> obs = findThingsForTypeAndKey(typeRegistry.getType(value), key);
            Observable<Thing<V>> result = findThingsForValue(obs, value);
            return result;
    }

    public Observable<? extends Thing<?>> findThingsForType(String type) {
            return findThingsMatchingTypeAndKey(type, "*");
    }



    public <V> Observable<Thing<V>> findThingsForValue(V value) {
            Observable<? extends Thing<?>> obs = findThingsForType(typeRegistry.getType(value));
            Observable<Thing<V>> result = findThingsForValue(obs, value).map(t -> (Thing<V>) t);
            return result;

    }

    public <V> Observable<Thing<V>> findThingsForValue(Observable<? extends Thing<?>> things, V value) {

        return things.filter(t -> equalsValue(t, value)).map(t -> (Thing<V>) t);
    }

    public Observable<? extends Thing<?>> findThingsMatchingKey(String keyMatcher) {
        return findThingsMatchingTypeAndKey("*", keyMatcher);
    }

    public <V> Observable<Thing<V>> findThingsMatchingKeyAndValue(String keyMatcher, V value) {
        Observable<? extends Thing<?>> obs = findThingsMatchingTypeAndKey(typeRegistry.getType(value), keyMatcher);
        Observable<Thing<V>> result = findThingsForValue(obs, value);
        return result;
    }

    public Observable<? extends Thing<?>> findThingsMatchingType(String typeMatcher) {
        return findThingsMatchingTypeAndKey(typeMatcher, "*");
    }

    public <V> Observable<Thing<V>> findThingsMatchingTypeAndKey(final Class<V> type,
                                                                 final String key) {

        return findThingsMatchingTypeAndKey(typeRegistry.getType(type), key).map(t -> (Thing<V>) t);
    }

    public Observable<? extends Thing<?>> getChildrenForKey(Observable<? extends Thing<?>> things, String key) {

        if ( MatcherUtils.isGlob(key) ) {
            throw new ThingRuntimeException("Key can't be glob for this query: " + key);
        }

        return getChildrenMatchingTypeAndKey(things, "*", key);
    }

    public Observable<? extends Thing<?>> getChildrenForType(Observable<? extends Thing<?>> things, String type) {

        if ( MatcherUtils.isGlob(type) ) {
            throw new ThingRuntimeException("Type can't be glob for this query: " + type);
        }

        return getChildrenMatchingTypeAndKey(things, type, "*");
    }

    public Observable<? extends Thing<?>> getChildrenForTypeAndKey(Observable<? extends Thing<?>> things, String type, String key) {

        if ( MatcherUtils.isGlob(type) ) {
            throw new ThingRuntimeException("Type can't be glob for this query: " + type);
        }
        if ( MatcherUtils.isGlob(key) ) {
            throw new ThingRuntimeException("Key can't be glob for this query: " + key);
        }

        return getChildrenMatchingTypeAndKey(things, type, "*");
    }

    public <V> Observable<Thing<V>> getChildrenForValue(Observable<? extends Thing<?>> thing, V value) {
        return findThingsForValue(thing.flatMap(t -> getChildrenForId(t.getId())), value);
    }

    public Observable<? extends Thing<?>> getChildrenMatchingKey(Observable<? extends Thing<?>> things, String keyMatcher) {
        return getChildrenMatchingTypeAndKey(things, "*", keyMatcher);
    }

    public Observable<? extends Thing<?>> getChildrenMatchingType(Observable<? extends Thing<?>> things, String typeMatcher) {
        return getChildrenMatchingTypeAndKey(things, typeMatcher, "*");
    }

    @Inject
    public void setTypeRegistry(TypeRegistry typeRegistry) {
        this.typeRegistry = typeRegistry;
    }

}
