package things.thing;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import things.exceptions.ThingException;
import things.exceptions.ValueException;

import javax.inject.Singleton;
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
public class ThingControl extends ThingControlReactive {


    private static final Logger myLogger = LoggerFactory.getLogger(ThingControl.class);

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
     * @throws ValueException                   if there is something wrong with the Value
     */
    public <V> Thing<V> createThing(String key, V value)
            throws ThingException, ValueException {
        Observable<Thing<V>> obs = observeCreateThing(key, value);

        return obs.toBlockingObservable().single();
    }

    public List<Thing> findAllThings() {
        return Lists.newArrayList(observeAllThings(true).toBlockingObservable().toIterable());
    }

    public <V> List<Thing<V>> findThingsForType(Class<V> typeClass) {
        return Lists.newArrayList(observeThingsForType(typeClass, true).toBlockingObservable().toIterable());
    }

    public List<Thing> findThingsForType(String type) {
        return Lists.newArrayList(observeThingsForType(type, true).toBlockingObservable().toIterable());
    }

    public <V> List<Thing<V>> findThingsForTypeAndKey(Class<V> typeClass, String key, boolean populated) {
        Observable<Thing<V>> obs = observeThingsForTypeAndKey(typeClass, key, populated);
        return Lists.newArrayList(obs.toBlockingObservable().toIterable());
    }

    public List<? extends Thing<?>> findThingsForTypeAndKey(String type, String key, boolean populated) {
        Observable<? extends Thing<?>> obs = observeThingsForTypeAndKey(type, key, populated);
        return Lists.newArrayList(obs.toBlockingObservable().toIterable());
    }

    public List<Thing> findThingsMatchingType(String type) {
        return Lists.newArrayList(observeThingsMatchingType(type, true).toBlockingObservable().toIterable());
    }

    public List<Thing> findThingsMatchingTypeAndKey(String typeMatch, String keyMatch) {
        return Lists.newArrayList(observeThingsMatchingTypeAndKey(typeMatch, keyMatch, true).toBlockingObservable().toIterable());
    }

    public <V> Optional<Thing<V>> findUniqueThingMatchingTypeAndKey(Class<V> type, String key, boolean popluateValue) {
        Observable<Thing<V>> obs = observeUniqueThingMatchingTypeAndKey(type, key, popluateValue);
        try {
            myLogger.debug("Finding unique thing for: " + typeRegistry.getType(type) + "/" + key);
            Thing<V> t = obs.toBlockingObservable().single();
            return Optional.of(t);
        } catch (NoSuchElementException nsee) {
            return Optional.empty();
        }
    }

    public Optional<? extends Thing<?>> findUniqueThingMatchingTypeAndKey(String type, String key, boolean popluateValue) {
        Observable<? extends Thing<?>> obs = observeUniqueThingMatchingTypeAndKey(type, key, popluateValue);
        try {
            Thing<?> t = obs.toBlockingObservable().single();
            return Optional.of(t);
        } catch (NoSuchElementException nsee) {
            return Optional.empty();
        }
    }

    public List<? extends Thing<?>> getChildren(Observable<? extends Thing<?>> things) {
        return Lists.newArrayList(observeChildren(things, true).toBlockingObservable().toIterable());
    }

    public <V> List<Thing<V>> getChildrenForType(Observable<? extends Thing<?>> things, Class<V> typeClass, boolean populateValues) {
        return Lists.newArrayList(observeChildrenForType(things, typeClass, populateValues).toBlockingObservable().toIterable());
    }

    public <V> List<Thing<V>> getChildrenForType(Thing<?> thing, Class<V> typeClass, boolean populateValues) {
        return Lists.newArrayList(observeChildrenForType(Observable.just(thing), typeClass, populateValues).toBlockingObservable().toIterable());
    }

    public List<Thing> getChilds(Thing t) {
        return Lists.newArrayList(observeChildren(t, true).toBlockingObservable().toIterable());
    }

    public List<Thing> getChildsMatchingType(Observable<? extends Thing<?>> things, String type) {
        return Lists.newArrayList(observeChildrenMatchingType(things, type, true).toBlockingObservable().toIterable());
    }

    public List<? extends Thing<?>> getChildsMatchingTypeAndKey(Observable<? extends Thing<?>> things, String typeMatch, String keyMatch) {
        return Lists.newArrayList(observeChildrenMatchingTypeAndKey(things, typeMatch, keyMatch, true).toBlockingObservable().toIterable());
    }

    public Observable<? extends Thing<?>> observeUniqueThingMatchingTypeAndKey(String type, String key, boolean populateValue) {
        Observable<? extends Thing<?>> obs = observeThingsMatchingTypeAndKey(type, key, false).single();

        if ( populateValue ) {
            return obs.lift(POPULATE_THINGS);
        } else {
            return obs;
        }
    }

    /**
     * Checks whether a thing with the specified type and key exists.
     *
     * @param typeMatch the type (or type-glob)
     * @param keyMatch  the key (or key-glob)
     * @return whether such a Thing exists
     */
    public boolean thingForTypeAndKeyExists(final String typeMatch, final String keyMatch) {

        Observable<? extends Thing<?>> obs = observeThingsForTypeAndKey(typeMatch, keyMatch, false);
        return !obs.isEmpty().toBlockingObservable().single();
    }


    /**
     * Checks whether a thing with the specified type and key exists.
     *
     * @param type the type
     * @param key  the key (or key-glob)
     * @return whether such a Thing exists
     */
    public boolean thingForTypeAndKeyExists(Class type, String key) {
        return thingForTypeAndKeyExists(typeRegistry.getType(type), key);
    }

    /**
     * Checks whether a thing with the specified type and key exists.
     *
     * @param type the type
     * @param key  the key (or key-glob)
     * @return whether such a Thing exists
     */
    public boolean thingMatchingTypeAndKeyExists(Class type, String key) {
        return thingMatchingTypeAndKeyExists(typeRegistry.getType(type), key);
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
