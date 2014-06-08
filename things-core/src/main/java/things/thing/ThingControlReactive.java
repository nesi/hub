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

import rx.Observable;
import things.exceptions.ThingRuntimeException;
import things.utils.MatcherUtils;

import java.util.List;
import java.util.Optional;

/**
 * Convenience class that extends {@link things.thing.ThingControl} and adds some convenience methods
 * for (mostly) query purposes.
 *
 * Note that all of those methods return {@link rx.Observable}s (also, the start with 'observe*', opposed to 'get*'
 * for the methods in {@link things.thing.ThingControl}), which can be used to chain filters and additional queries together.
 *
 * For more details about the overall workings of this class check out {@link things.thing.ThingControl}.
 */
public class ThingControlReactive extends ThingControlMinimal {

    public ThingControlReactive() {
        super();
    }

    // might be dangerous, since addChildThing result won't be updated with result from hibernate session
//    public Thing<?> addChildThingToObservable(Observable<? extends Thing<?>> parents, Thing<?> child) {
//        parents.map(p -> addChildThing(p, child)).toBlockingObservable();
//        return child;
//    }

    public <V> Observable<Thing<V>> filterThingsOfType(Class<V> type, Observable<? extends Thing<?>> things) {
        return things.filter(t -> typeRegistry.equals(type, t.getThingType())).map(t -> populateAndConvertToTyped(type, t));
    }


    public Observable<? extends Thing<?>> observeChildrenForKey(Observable<? extends Thing<?>> things, String key, boolean populateValues) {

        if ( MatcherUtils.isGlob(key) ) {
            throw new ThingRuntimeException("Key can't be glob for this query: " + key);
        }

        Observable<? extends Thing<?>> result = observeChildrenMatchingTypeAndKey(things, "*", key, populateValues);
        return result;
    }

    public <V> Observable<Thing<V>> observeChildrenForType(Thing<?> thing, Class<V> typeClass, boolean populateValues) {
        return observeChildrenForType(Observable.just(thing), typeClass, populateValues);
    }

    public <V> Observable<Thing<V>> observeChildrenForType(Observable<? extends Thing<?>> things, Class<V> typeClass, boolean populateValues) {

        Observable<? extends Thing<?>> result = observeChildrenMatchingTypeAndKey(things, typeRegistry.getType(typeClass), "*", populateValues);
        return result.map(t -> populateAndConvertToTyped(typeClass, t));
    }

    public Observable<? extends Thing<?>> observeChildrenForType(Observable<? extends Thing<?>> things, String type, boolean populateValues) {


        if ( MatcherUtils.isGlob(type) ) {
            throw new ThingRuntimeException("Type can't be glob for this query: " + type);
        }

        Observable<? extends Thing<?>> result = observeChildrenMatchingTypeAndKey(things, type, "*", populateValues);
        return result;
    }

    public Observable<? extends Thing<?>> observeChildrenMatchingKey(Observable<? extends Thing<?>> things, String key, boolean populateValues) {

        Observable<? extends Thing<?>> result = observeChildrenMatchingTypeAndKey(things, "*", key, populateValues);
        return result;
    }

    public Observable<? extends Thing<?>> observeChildrenMatchingType(Observable<? extends Thing<?>> things, String type, boolean populateValues) {

        Observable<? extends Thing<?>> result = observeChildrenMatchingTypeAndKey(things, type, "*", populateValues);
        return result;
    }

    public Observable<? extends Thing<?>> observeChildrenMatchingTypeAndKey(Thing<?> t, String typeMatch, String keyMatch, boolean populated) {
        return observeChildrenMatchingTypeAndKey(Observable.just(t), typeMatch, keyMatch, populated);
    }

    public Observable<? extends Thing<?>> observeChildrenMatchingTypeAndKey(List<? extends Thing<?>> t, String typeMatch, String keyMatch, boolean populated) {
        return observeChildrenMatchingTypeAndKey(Observable.from(t), typeMatch, keyMatch, populated);
    }

    public <V> Observable<Thing<V>> observeParentsOfType(Thing<?> t, Class<V> parentType, boolean populate) {
        return observeParents(Observable.just(t), Optional.of(typeRegistry.getType(parentType)), Optional.of("*"), populate).map(thing -> (Thing<V>)thing);
    }

    public Observable<? extends Thing<?>> observeParents(Thing<?> t, Optional<String> type, Optional<String> key, boolean populate) {
        return observeParents(Observable.just(t), type, key, populate);
    }

    public Observable<? extends Thing<?>> observeParents(Thing<?> t) {
        return observeParents(t, Optional.empty(), Optional.empty(), false);
    }

    public Observable<? extends Thing<?>> observeParents(Observable<? extends Thing<?>> things, Optional<String> type, Optional<String> key, boolean populate) {

        return things.flatMap(t -> observeThingsById(Observable.from(t.getParents()), type, key, populate));

    }

    public Observable<? extends Thing<?>> observeThingsById(Observable<String> id, boolean populate) {
        return id.flatMap(i -> observeThingById(i, Optional.empty(), Optional.empty(), populate));
    }

    public Observable<? extends Thing<?>> observeThingsById(Observable<String> id, Optional<String> type, Optional<String> key, boolean populate) {
        return id.flatMap(i -> observeThingById(i, type, key, populate));
    }

    public <V> Observable<Thing<V>> observeThingsForType(Class<V> typeClass, boolean populateValues) {

        Observable<? extends Thing<?>> result = observeThingsMatchingTypeAndKey(typeRegistry.getType(typeClass), "*", populateValues);
        return result.map(t -> populateAndConvertToTyped(typeClass, t));
    }

    public Observable<? extends Thing<?>> observeThingsForType(String type, boolean populateValues) {

        if ( MatcherUtils.isGlob(type) ) {
            throw new ThingRuntimeException("Type can't be glob for this query: " + type);
        }

        Observable<? extends Thing<?>> result = observeThingsMatchingTypeAndKey(type, "*", populateValues);
        return result;
    }

    public <V> Observable<Thing<V>> observeThingsForTypeAndKey(Class<V> typeClass, String key, boolean populate) {

        return observeThingsForTypeAndKey(typeRegistry.getType(typeClass), key, populate)
                .map(t -> populateAndConvertToTyped(typeClass, t));

    }


    public Observable<? extends Thing<?>> observeThingsMatchingType(String type, boolean populateValues) {
        Observable<? extends Thing<?>> obs = observeThingsMatchingTypeAndKey(type, "*", populateValues);
        return obs;
    }

    public <V> Observable<Thing<V>> observeUniqueThingMatchingKeyAndValue(String key, V value) {
        return observeThingsMatchingKeyAndValue(key, value).single();
    }

    public Observable<? extends Thing<?>> observeUniqueThingMatchingTypeAndKey(String type, String key, boolean populateValue) {
        Observable<? extends Thing<?>> obs = observeThingsMatchingTypeAndKey(type, key, false).single();

        if ( populateValue ) {
            return obs.lift(POPULATE_THINGS);
        } else {
            return obs;
        }
    }

    public <V> Observable<Thing<V>> observeUniqueThingMatchingTypeAndKey(Class<V> type, String key, boolean populateValue) {
        Observable<? extends Thing<?>> obs = observeThingsMatchingTypeAndKey(typeRegistry.getType(type), key, populateValue).single();

        try {
            return obs.map(t -> populateAndConvertToTyped(type, t)).single();
        } catch (IllegalArgumentException iae) {
            throw new ThingRuntimeException("Too many results for type '" + type + "' and key '" + key + "'");
        }


    }

}
