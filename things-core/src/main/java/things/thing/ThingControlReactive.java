package things.thing;

import com.google.common.collect.Lists;
import rx.Observable;
import things.exceptions.ThingRuntimeException;
import things.utils.MatcherUtils;

import java.util.List;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 17/05/14
 * Time: 8:02 PM
 */
public class ThingControlReactive extends ThingControlMinimal {

    public ThingControlReactive() {
        super();
    }

    public Thing<?> addChildThingToObservable(Observable<? extends Thing<?>> parents, Thing<?> child) {
        parents.map(p -> addChildThing(p, child)).toBlockingObservable();
        return child;
    }

    public <V> Observable<Thing<V>> filterThingsOfType(Class<V> type, Observable<? extends Thing<?>> things) {
        return things.filter(t -> typeRegistry.equals(type, t.getThingType())).map(t -> populateAndConvertToTyped(type, t));
    }

    public List<? extends Thing<?>> findParents(Thing<?> t) {
        return findParents(Observable.just(t));
    }

    public List<? extends Thing<?>> findParents(Observable<? extends Thing<?>> things) {

        return Lists.newArrayList(observeParents(things).toBlockingObservable().toIterable());

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

    public Observable<? extends Thing<?>> observeUniqueThingMatchingTypeAndKey(String type, String key, boolean populateValue) {
        Observable<? extends Thing<?>> obs = observeThingsMatchingTypeAndKey(type, key, false).single();

        if ( populateValue ) {
            return obs.lift(POPULATE_THINGS);
        } else {
            return obs;
        }
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

    public Observable<? extends Thing<?>> observeParents(Thing<?> t) {
        return observeParents(Observable.just(t));
    }

    public Observable<? extends Thing<?>> observeParents(Observable<? extends Thing<?>> things) {

        return things.flatMap(t -> observeThingsById(Observable.from(t.getParents())));

    }

    public Observable<? extends Thing<?>> observeThingsById(Observable<String> id) {
        return id.flatMap(i -> observeThingById(i));
    }

    public <V> Observable<Thing<V>> observeThingsForType(Class<V> typeClass, boolean populateValues) {

        Observable<? extends Thing<?>> result = observeThingsMatchingTypeAndKey(typeRegistry.getType(typeClass), "*", populateValues);
        return result.map(t -> populateAndConvertToTyped(typeClass, t));
    }

    public Observable<? extends Thing<?>> observeThingsForType(String type, boolean populateValues) {

        if ( MatcherUtils.isGlob(type) ) {
            throw new ThingRuntimeException("Type can't be glob for this query: "+type);
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

    public <V> Observable<Thing<V>> observeUniqueThingMatchingTypeAndKey(Class<V> type, String key, boolean populateValue) {
        Observable<? extends Thing<?>> obs = observeThingsMatchingTypeAndKey(typeRegistry.getType(type), key, populateValue).single();

        try {
            return obs.map(t -> populateAndConvertToTyped(type, t)).single();
        } catch (IllegalArgumentException iae) {
            throw new ThingRuntimeException("Too many results for type '" + type + "' and key '" + key + "'");
        }


    }

}
