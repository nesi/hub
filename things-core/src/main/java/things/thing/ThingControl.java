package things.thing;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import rx.Observable;
import things.exceptions.ThingException;
import things.exceptions.ValueException;

import javax.inject.Inject;
import javax.validation.Validator;
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


    @Inject
    public ThingControl(ThingReaders thingReaders, ThingWriters thingWriters, Validator validator) {
        super(thingReaders, thingWriters, validator);
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

    private <V> Observable<Thing<V>> filterThingsOfType(Class<V> type, Observable<Thing<V>> things) {
        return things.filter(t -> TypeRegistry.equalsType(type, t.getThingType())).map(t -> convertToTyped(type, t));
    }

    /**
     * Checks whether a thing with the specified type and key exists.
     *
     * @param type the type
     * @param key  the key (or key-glob)
     * @return whether such a Thing exists
     */
    public boolean thingWithTypeAndKeyExists(Class type, String key) {
        return thingWithTypeAndKeyExists(TypeRegistry.getType(type), key);
    }

    public <V> Observable<Thing<V>> observeThingsForType(Class<V> typeClass, boolean populateValues) {
        Observable<Thing> result = observeThingsMatchingTypeAndKey(TypeRegistry.getType(typeClass), "*", populateValues);
        return result.map(t -> convertToTyped(typeClass, t));
    }

    public Observable<Thing> observeThingsForType(String type, boolean populateValues) {
        Observable<Thing> result = observeThingsMatchingTypeAndKey(type, "*", populateValues);
        return result;
    }

    public <V> List<Thing<V>> findThingsForType(Class<V> typeClass) {
        return Lists.newArrayList(observeThingsForType(typeClass, true).toBlockingObservable().toIterable());
    }

    public List<Thing> findThingsForType(String type) {
        return Lists.newArrayList(observeThingsForType(type, true).toBlockingObservable().toIterable());
    }

    public <V> Observable<Thing<V>> observeChildsForType(Thing thing, Class<V> typeClass, boolean populateValues) {

        Observable<Thing> result = observeChildsMatchingTypeAndKey(thing, TypeRegistry.getType(typeClass), "*", populateValues);
        return result.map(t -> convertToTyped(typeClass, t));
    }

    public <V> List<Thing<V>> getChildsForType(Thing thing, Class<V> typeClass) {
        return Lists.newArrayList(observeChildsForType(thing, typeClass, true).toBlockingObservable().toIterable());
    }

    public Observable<Thing> observeChildsMatchingType(Thing thing, String type, boolean populateValues) {

        Observable<Thing> result = observeChildsMatchingTypeAndKey(thing, type, "*", populateValues);
        return result;
    }

    public List<Thing> getChildsMatchingType(Thing thing, String type) {
        return Lists.newArrayList(observeChildsMatchingType(thing, type, true).toBlockingObservable().toIterable());
    }

    public Observable<Thing> observeAllThings(boolean populateValues) {
        List<Observable<Thing>> all = Lists.newArrayList();
        for (ThingReader r : thingReaders.getAll()) {
            all.add(r.findAllThings());
        }

        Observable<Thing> obs = Observable.merge(all);
        if ( populateValues ) {
            return ensurePolulatedValueUntyped(obs);
        }
        return obs;
    }

    public List<Thing> findAllThings() {
        return Lists.newArrayList(observeAllThings(true).toBlockingObservable().toIterable());
    }

    public Observable<Thing> observeUniqueThingMatchingTypeAndKey(String type, String key, boolean populateValue) {
        Observable<Thing> obs = observeThingsMatchingTypeAndKey(type, key, false).single();
        if ( populateValue ) {
            return ensurePolulatedValueUntyped(obs);
        } else {
            return obs;
        }
    }

    public Optional<Thing> findUniqueThingMatchingTypeAndKey(String type, String key, boolean popluateValue) {
        Observable<Thing> obs = observeUniqueThingMatchingTypeAndKey(type, key, false);
        try {
            Thing t = obs.toBlockingObservable().single();
            if ( popluateValue ) {
                t = ensurePoplutedValue(t);
            }
            return Optional.of(t);
        } catch (NoSuchElementException nsee) {
            return Optional.empty();
        }
    }

    public Observable<Thing> observeThingsMatchingType(String type, boolean populateValues) {
        Observable<Thing> obs = observeThingsMatchingTypeAndKey(type, "*", populateValues);
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

    public Observable<Thing> observeChilds(Observable<Thing> things, boolean populate) {

        return things.flatMap(t -> observeChilds(t, populate));
    }

    public Observable<Thing> observeChilds(Thing t, boolean populate) {

        List<Observable<Thing>> observables = Lists.newLinkedList();
        //TODO why does the below doesn't work for String?
        for (Object link : t.getOtherThings()) {

            Observable<Thing> child = findThingForId(extractReaderName((String)link), extractId((String)link));
            observables.add(child);

        }

        Observable<Thing> obs = Observable.merge(observables);
        if ( populate ) {
            return ensurePolulatedValueUntyped(obs);
        } else {
            return obs;
        }
    }


    public Observable<Thing> observeChildsMatchingTypeAndKey(Observable<Thing> things, String typeMatch, String keyMatch, boolean populated) {
        return things.flatMap(t -> observeChildsMatchingTypeAndKey(t, typeMatch, keyMatch, populated));
    }

    public List<Thing> getChildsMatchingTypeAndKey(Observable<Thing> things, String typeMatch, String keyMatch) {
        return Lists.newArrayList(observeChildsMatchingTypeAndKey(things, typeMatch, keyMatch, true).toBlockingObservable().toIterable());
    }

    public List<Thing> getChilds(Observable<Thing> things) {
        return Lists.newArrayList(observeChilds(things, true).toBlockingObservable().toIterable());
    }

    public List<Thing> getChilds(Thing t) {
        return Lists.newArrayList(observeChilds(t, true).toBlockingObservable().toIterable());
    }


//    public Optional<Thing> findThingById(String type, Object id) {
//
//        List<Optional<Thing>> result = getThingReadersMatchingType(type)
//                .parallelStream().map(c -> c.findThingById(id))
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

//    public Optional<Thing> findThingById(Class<Person> typeClass, Object id) {
//        return findThingById(TypeRegistry.getType(typeClass), id);
//    }
}
