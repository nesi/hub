package things.thing;

import com.google.common.collect.Lists;
import rx.Observable;
import things.exceptions.ThingRuntimeException;

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

    public <V> Observable<Thing<V>> filterThingsOfType(Class<V> type, Observable<? extends Thing<?>> things) {
        return things.filter(t -> typeRegistry.equals(type, t.getThingType())).map(t -> convertToTyped(type, t));
    }

    public <V> Observable<Thing<V>> observeThingsForType(Class<V> typeClass, boolean populateValues) {
        Observable<? extends Thing<?>> result = observeThingsMatchingTypeAndKey(typeRegistry.getType(typeClass), "*", populateValues);
        return result.map(t -> convertToTyped(typeClass, t));
    }

    public Observable<? extends Thing<?>> observeThingsForType(String type, boolean populateValues) {
        Observable<? extends Thing<?>> result = observeThingsMatchingTypeAndKey(type, "*", populateValues);
        return result;
    }

    public <V> Observable<Thing<V>> observeChildrenForType(Thing<?> thing, Class<V> typeClass, boolean populateValues) {
        return observeChildrenForType(Observable.just(thing), typeClass, populateValues);
    }

    public <V> Observable<Thing<V>> observeChildrenForType(Observable<? extends Thing<?>> things, Class<V> typeClass, boolean populateValues) {

        Observable<? extends Thing<?>> result = observeChildrenMatchingTypeAndKey(things, typeRegistry.getType(typeClass), "*", populateValues);
        return result.map(t -> convertToTyped(typeClass, t));
    }

    public Observable<? extends Thing<?>> observeChildsMatchingType(Observable<? extends Thing<?>> things, String type, boolean populateValues) {

        Observable<? extends Thing<?>> result = observeChildrenMatchingTypeAndKey(things, type, "*", populateValues);
        return result;
    }

    public <V> Observable<Thing<V>> observeUniqueThingMatchingKeyAndValue(String key, V value) {
        return observeThingsMatchingKeyAndValue(key, value).single();
    }

    public Observable<? extends Thing<?>> observeAllThings(boolean populateValues) {
        List<Observable<? extends Thing<?>>> all = Lists.newArrayList();
        for (ThingReader r : thingReaders.getAll()) {
            all.add(r.findAllThings());
        }

        Observable<? extends Thing<?>> obs = Observable.merge(all);
        if ( populateValues ) {
            return obs.lift(POPULATE_THINGS);
        }
        return obs;
    }


    public <V> Observable<Thing<V>> observeUniqueThingMatchingTypeAndKey(Class<V> type, String key, boolean populateValue) {
        Observable<? extends Thing<?>> obs = observeThingsMatchingTypeAndKey(typeRegistry.getType(type), key, populateValue).single();

        try {
            return obs.map(t -> convertToTyped(type, t)).single();
        } catch (IllegalArgumentException iae) {
            throw new ThingRuntimeException("Too many results for type '" +type+"' and key '"+key+"'");
        }


    }

    public Observable<? extends Thing<?>> observeThingsMatchingType(String type, boolean populateValues) {
        Observable<? extends Thing<?>> obs = observeThingsMatchingTypeAndKey(type, "*", populateValues);
        return obs;
    }

        public Observable<? extends Thing<?>> observeChilds(Observable<? extends Thing<?>> things, boolean populate) {

        return things.flatMap(t -> observeChilds(t, populate));
    }



    public Observable<? extends Thing<?>> observeChildrenMatchingTypeAndKey(Thing<?> t, String typeMatch, String keyMatch, boolean populated) {
        return observeChildrenMatchingTypeAndKey(Observable.just(t), typeMatch, keyMatch, populated);
    }

    public Observable<? extends Thing<?>> observeChildrenMatchingTypeAndKey(List<? extends Thing<?>> t, String typeMatch, String keyMatch, boolean populated) {
        return observeChildrenMatchingTypeAndKey(Observable.from(t), typeMatch, keyMatch, populated);
    }

}
