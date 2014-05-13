package things.thing;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import rx.Observable;
import things.exceptions.ThingException;
import things.exceptions.ValueException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 6/05/14
 * Time: 8:21 PM
 */
@Singleton
public class ThingControl extends ThingControlMinimal {


    public ThingControl() {
        super();
    }

    /**
     * Creates a Thing using the provided key and Value and persists it.
     * <p>
     * Before any persisting occurs, the Value will be validated.
     * <p>
     * If the Value is not persisted yet, it will be using the configured
     * {@link ThingWriter} before the Thing itself is
     * saved.
     *
     * @param key   the key
     * @param value the Value
     * @return the newly created and persisted Thing (including a shiny new
     * Thing id)
     * @throws things.exceptions.ThingException if the Thing can't be persisted for some reason
     * @throws ValueException if there is something wrong with the Value
     */
    public <V> Thing<V> createThing(String key, V value)
            throws ThingException, ValueException {
        Observable<Thing<V>> obs = observeCreateThing(key, value);

        return obs.toBlockingObservable().single();
    }

    public <V> Observable<Thing<V>> filterThingsOfType(Class<V> type, Observable<? extends Thing<?>> things) {
        return things.filter(t -> TypeRegistry.equalsType(type, t.getThingType())).map(t -> convertToTyped(type, t));
    }

    /**
     * Checks whether a thing with the specified type and key exists.
     *
     * @param type the type
     * @param key  the key (or key-glob)
     * @return whether such a Thing exists
     */
    public boolean thingMatchingTypeAndKeyExists(Class type, String key) {
        return thingMatchingTypeAndKeyExists(TypeRegistry.getType(type), key);
    }

    /**
     * Checks whether a thing with the specified type and key exists.
     *
     * @param type the type
     * @param key  the key (or key-glob)
     * @return whether such a Thing exists
     */
    public boolean thingForTypeAndKeyExists(Class type, String key) {
        return thingForTypeAndKeyExists(TypeRegistry.getType(type), key);
    }

    public <V> Observable<Thing<V>> observeThingsForType(Class<V> typeClass, boolean populateValues) {
        Observable<? extends Thing<?>> result = observeThingsMatchingTypeAndKey(TypeRegistry.getType(typeClass), "*", populateValues);
        return result.map(t -> convertToTyped(typeClass, t));
    }

    public Observable<? extends Thing<?>> observeThingsForType(String type, boolean populateValues) {
        Observable<? extends Thing<?>> result = observeThingsMatchingTypeAndKey(type, "*", populateValues);
        return result;
    }

    public <V> List<Thing<V>> findThingsForType(Class<V> typeClass) {
        return Lists.newArrayList(observeThingsForType(typeClass, true).toBlockingObservable().toIterable());
    }

    public List<Thing> findThingsForType(String type) {
        return Lists.newArrayList(observeThingsForType(type, true).toBlockingObservable().toIterable());
    }

    public <V> Observable<Thing<V>> observeChildrenForType(Thing<?> thing, Class<V> typeClass, boolean populateValues) {
        return observeChildrenForType(Observable.just(thing), typeClass, populateValues);
    }

    public <V> Observable<Thing<V>> observeChildrenForType(Observable<? extends Thing<?>> things, Class<V> typeClass, boolean populateValues) {

        Observable<? extends Thing<?>> result = observeChildrenMatchingTypeAndKey(things, TypeRegistry.getType(typeClass), "*", populateValues);
        return result.map(t -> convertToTyped(typeClass, t));
    }

    public <V> List<Thing<V>> getChildrenForType(Observable<? extends Thing<?>> things, Class<V> typeClass) {
        return Lists.newArrayList(observeChildrenForType(things, typeClass, true).toBlockingObservable().toIterable());
    }

    public Observable<? extends Thing<?>> observeChildsMatchingType(Observable<? extends Thing<?>> things, String type, boolean populateValues) {

        Observable<? extends Thing<?>> result = observeChildrenMatchingTypeAndKey(things, type, "*", populateValues);
        return result;
    }

    public List<Thing> getChildsMatchingType(Observable<? extends Thing<?>> things, String type) {
        return Lists.newArrayList(observeChildsMatchingType(things, type, true).toBlockingObservable().toIterable());
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

    public List<Thing> findAllThings() {
        return Lists.newArrayList(observeAllThings(true).toBlockingObservable().toIterable());
    }

    public Observable<? extends Thing<?>> observeUniqueThingMatchingTypeAndKey(String type, String key, boolean populateValue) {
        Observable<? extends Thing<?>> obs = observeThingsMatchingTypeAndKey(type, key, false).single();
        if ( populateValue ) {
            return obs.lift(POPULATE_THINGS);
        } else {
            return obs;
        }
    }

    public Optional<? extends Thing<?>> findUniqueThingMatchingTypeAndKey(String type, String key, boolean popluateValue) {
        Observable<? extends Thing<?>> obs = observeUniqueThingMatchingTypeAndKey(type, key, false);
        try {
            Thing<?> t = obs.toBlockingObservable().single();
            if ( popluateValue ) {
                t = ensurePopulatedValue(t);
            }
            return Optional.of(t);
        } catch (NoSuchElementException nsee) {
            return Optional.empty();
        }
    }

    public Observable<? extends Thing<?>> observeThingsMatchingType(String type, boolean populateValues) {
        Observable<? extends Thing<?>> obs = observeThingsMatchingTypeAndKey(type, "*", populateValues);
        return obs;
    }

    public List<Thing> findThingsMatchingType(String type) {
        return Lists.newArrayList(observeThingsMatchingType(type, true).toBlockingObservable().toIterable());
    }

    public List<Thing> findThingsMatchingTypeAndKey(String typeMatch, String keyMatch) {
        return Lists.newArrayList(observeThingsMatchingTypeAndKey(typeMatch, keyMatch, true).toBlockingObservable().toIterable());
    }

    public static String extractReaderName(String id) {
        int i = id.indexOf("/");
        return id.substring(0, i);
    }

    public static String extractId(String id) {
        int i = id.indexOf("/");
        return id.substring(i+1);
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

    public List<? extends Thing<?>> getChildsMatchingTypeAndKey(Observable<? extends Thing<?>> things, String typeMatch, String keyMatch) {
        return Lists.newArrayList(observeChildrenMatchingTypeAndKey(things, typeMatch, keyMatch, true).toBlockingObservable().toIterable());
    }

    public List<? extends Thing<?>> getChildren(Observable<? extends Thing<?>> things) {
        return Lists.newArrayList(observeChilds(things, true).toBlockingObservable().toIterable());
    }

    public List<Thing> getChilds(Thing t) {
        return Lists.newArrayList(observeChilds(t, true).toBlockingObservable().toIterable());
    }


    public <V> List<Thing<V>> findThingsForTypeAndKey(Class<V> typeClass, String key, boolean populated) {
        Observable<Thing<V>> obs = observeThingsForTypeAndKey(typeClass, key, populated);
        return Lists.newArrayList(obs.toBlockingObservable().toIterable());
    }

    public List<? extends Thing<?>> findThingsForTypeAndKey(String type, String key, boolean populated) {
        Observable<? extends Thing<?>> obs = observeThingsForTypeAndKey(type, key, populated);
        return Lists.newArrayList(obs.toBlockingObservable().toIterable());
    }

//    public Optional<Thing> findUniqueThingMatchingKeyAndValue(String key, Object value) {
//        return findv
//    }


//    public Optional<Thing> observeThingById(String type, Object id) {
//
//        List<Optional<Thing>> result = getThingReadersMatchingType(type)
//                .parallelStream().map(c -> c.observeThingById(id))
//                .filter(o -> o.isPresent())
//                .collect(Collectors.toList());
//
//        if ( result.size() == 0 ) {
//            return Optional.empty();
//        } else if ( result.size() > 1 ) {
//            throw new TypeRuntimeException("More than one element found for id: "+id, type);
//        } else {
//            return result.get(0);
//        }
//
//
//    }

//    public Optional<Thing> observeThingById(Class<Person> typeClass, Object id) {
//        return observeThingById(TypeRegistry.getType(typeClass), id);
//    }
}
