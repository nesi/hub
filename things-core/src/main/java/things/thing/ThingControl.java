package things.thing;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import things.exceptions.ThingException;
import things.exceptions.TypeRuntimeException;
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

    private static final Boolean POPULATED_BY_DEFAULT = true;

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

    public <V> List<Thing<V>> filterThingsWithValue(List<Thing> things, V value) {

        Observable<Thing<V>> obs = Observable.from(things).filter(t -> value.equals(getValue(t))).map(t -> (Thing<V>) t);

        return Lists.newArrayList(obs.toBlockingObservable().toIterable());
    }

    public List<Thing> findAllThings() {
        return findAllThings(POPULATED_BY_DEFAULT);
    }
    public List<Thing> findAllThings(boolean populated) {
        return Lists.newArrayList(observeAllThings(populated).toBlockingObservable().toIterable());
    }

    public <V> List<Thing<V>> findThingsForType(Class<V> typeClass) {
        return findThingsForType(typeClass, POPULATED_BY_DEFAULT);
    }

    public <V> List<Thing<V>> findThingsForType(Class<V> typeClass, boolean populated) {
        return Lists.newArrayList(observeThingsForType(typeClass, populated).toBlockingObservable().toIterable());
    }

    public List<Thing> findThingsForType(String type) {
        return findThingsForType(type, POPULATED_BY_DEFAULT);
    }

    public List<Thing> findThingsForType(String type, boolean populated) {
        return Lists.newArrayList(observeThingsForType(type, populated).toBlockingObservable().toIterable());
    }

    public <V> List<Thing<V>> findThingsForTypeAndKey(Class<V> typeClass, String key) {
        return findThingsForTypeAndKey(typeClass, key, POPULATED_BY_DEFAULT);
    }

    public <V> List<Thing<V>> findThingsForTypeAndKey(Class<V> typeClass, String key, boolean populated) {
        Observable<Thing<V>> obs = observeThingsForTypeAndKey(typeClass, key, populated);
        return Lists.newArrayList(obs.toBlockingObservable().toIterable());
    }

    public List<Thing> findThingsForTypeAndKey(String type, String key) {
        return findThingsForTypeAndKey(type, key, POPULATED_BY_DEFAULT);
    }

    public List<Thing> findThingsForTypeAndKey(String type, String key, boolean populated) {
        Observable<? extends Thing<?>> obs = observeThingsForTypeAndKey(type, key, populated);
        return Lists.newArrayList(obs.toBlockingObservable().toIterable());
    }

    public List<Thing> findThingsMatchingType(String type) {
        return findThingsMatchingType(type, POPULATED_BY_DEFAULT);
    }

    public List<Thing> findThingsMatchingType(String type, boolean populated) {
        return Lists.newArrayList(observeThingsMatchingType(type, populated).toBlockingObservable().toIterable());
    }

    public List<Thing> findThingsMatchingTypeAndKey(String typeMatch, String keyMatch) {
        return findThingsMatchingTypeAndKey(typeMatch, keyMatch, POPULATED_BY_DEFAULT);
    }

    public List<Thing> findThingsMatchingTypeAndKey(String typeMatch, String keyMatch, boolean populated) {
        return Lists.newArrayList(observeThingsMatchingTypeAndKey(typeMatch, keyMatch, populated).toBlockingObservable().toIterable());
    }

    public <V> Optional<Thing<V>> findUniqueThingMatchingTypeAndKey(Class<V> type, String key) {
        return findUniqueThingMatchingTypeAndKey(type, key, POPULATED_BY_DEFAULT);
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

    public Optional<Thing> findUniqueThingMatchingTypeAndKey(String type, String key) {
        return findUniqueThingMatchingTypeAndKey(type, key, POPULATED_BY_DEFAULT);
    }

    public Optional<Thing> findUniqueThingMatchingTypeAndKey(String type, String key, boolean popluateValue) {
        Observable<? extends Thing<?>> obs = observeUniqueThingMatchingTypeAndKey(type, key, popluateValue);
        try {
            Thing<?> t = obs.toBlockingObservable().single();
            return Optional.of(t);
        } catch (NoSuchElementException nsee) {
            return Optional.empty();
        }
    }

    public List<Thing> getChildren(Observable<? extends Thing<?>> things) {
        return getChildren(things, POPULATED_BY_DEFAULT);
    }

    public List<Thing> getChildren(Observable<? extends Thing<?>> things, boolean populated) {
        return Lists.newArrayList(observeChildren(things, populated).toBlockingObservable().toIterable());
    }

    public <V> List<Thing<V>> getChildrenForType(Observable<? extends Thing<?>> things, Class<V> typeClass) {
        return getChildrenForType(things, typeClass, POPULATED_BY_DEFAULT);
    }

    public <V> List<Thing<V>> getChildrenForType(Observable<? extends Thing<?>> things, Class<V> typeClass, boolean populateValues) {
        return Lists.newArrayList(observeChildrenForType(things, typeClass, populateValues).toBlockingObservable().toIterable());
    }

    public <V> List<Thing<V>> getChildrenForType(Thing<?> thing, Class<V> typeClass) {
        return getChildrenForType(thing, typeClass, POPULATED_BY_DEFAULT);
    }

    public <V> List<Thing<V>> getChildrenForType(Thing<?> thing, Class<V> typeClass, boolean populateValues) {
        return Lists.newArrayList(observeChildrenForType(Observable.just(thing), typeClass, populateValues).toBlockingObservable().toIterable());
    }

    public List<Thing> getChildren(Thing<?> t) {
        return getChildren(t, POPULATED_BY_DEFAULT);
    }

    public List<Thing> getChildren(Thing<?> t, boolean populated) {
        return Lists.newArrayList(observeChildren(t, populated).toBlockingObservable().toIterable());
    }

    public List<Thing> getChildrenMatchingType(Thing<?> thing, String type) {
        return getChildrenMatchingType(Observable.just(thing), type, POPULATED_BY_DEFAULT);
    }

    public List<Thing> getChildrenMatchingType(List<? extends Thing<?>> things, String type) {
        return getChildrenMatchingType(Observable.from(things), type, POPULATED_BY_DEFAULT);
    }

    public List<Thing> getChildrenMatchingType(Thing<?> thing, String type, boolean populated) {
        return getChildrenMatchingType(Observable.just(thing), type, populated);
    }

    public List<Thing> getChildrenMatchingType(List<? extends Thing<?>> things, String type, boolean populated) {
        return getChildrenMatchingType(Observable.from(things), type, populated);
    }

    public List<Thing> getChildrenMatchingType(Observable<? extends Thing<?>> things, String type) {
        return getChildrenMatchingType(things, type, POPULATED_BY_DEFAULT);
    }

    public List<Thing> getChildrenMatchingType(Observable<? extends Thing<?>> things, String type, boolean populated) {
        return Lists.newArrayList(observeChildrenMatchingType(things, type, populated).toBlockingObservable().toIterable());
    }

    public List<Thing> getChildrenMatchingTypeAndKey(Thing<?> thing, String typeMatch, String keyMatch) {
        return getChildrenMatchingTypeAndKey(Observable.just(thing), typeMatch, keyMatch, POPULATED_BY_DEFAULT);
    }

    public List<Thing> getChildrenMatchingTypeAndKey(Thing<?> thing, String typeMatch, String keyMatch, boolean populated) {
        return Lists.newArrayList(observeChildrenMatchingTypeAndKey(Observable.just(thing), typeMatch, keyMatch, populated).toBlockingObservable().toIterable());
    }

    public List<Thing> getChildrenMatchingTypeAndKey(List<? extends Thing<?>> things, String typeMatch, String keyMatch) {
        return getChildrenMatchingTypeAndKey(Observable.from(things), typeMatch, keyMatch, POPULATED_BY_DEFAULT);
    }

    public List<Thing> getChildrenMatchingTypeAndKey(List<? extends Thing<?>> things, String typeMatch, String keyMatch, boolean populated) {
        return Lists.newArrayList(observeChildrenMatchingTypeAndKey(Observable.from(things), typeMatch, keyMatch, populated).toBlockingObservable().toIterable());
    }

    public List<Thing> getChildrenMatchingTypeAndKey(Observable<? extends Thing<?>> things, String typeMatch, String keyMatch) {
        return getChildrenMatchingTypeAndKey(things, typeMatch, keyMatch, POPULATED_BY_DEFAULT);
    }

    public List<Thing> getChildrenMatchingTypeAndKey(Observable<? extends Thing<?>> things, String typeMatch, String keyMatch, boolean populated) {
        return Lists.newArrayList(observeChildrenMatchingTypeAndKey(things, typeMatch, keyMatch, populated).toBlockingObservable().toIterable());
    }



    /**
     * Checks whether a thing with the specified type and key exists.
     *
     * @param typeMatch the type (or type-glob)
     * @param keyMatch  the key (or key-glob)
     * @return whether such a Thing exists
     */
    public boolean thingForTypeAndKeyExists(final String typeMatch, final String keyMatch) {

        try {
            Observable<? extends Thing<?>> obs = observeThingsForTypeAndKey(typeMatch, keyMatch, false);
            return !obs.isEmpty().toBlockingObservable().single();
        } catch (TypeRuntimeException tre) {
            myLogger.debug("Can't find connector for type '{}' or key '{}", typeMatch, keyMatch, tre);
            return false;
        }
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

}
