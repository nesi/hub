package things.thing;

import java.util.*;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.Subscriber;
import things.config.ThingActions;
import things.config.ThingQueries;
import things.config.ThingReaders;
import things.config.ThingWriters;
import things.exceptions.ActionException;
import things.exceptions.ThingException;
import things.exceptions.ThingRuntimeException;
import things.exceptions.TypeRuntimeException;
import things.types.TypeRegistry;
import things.utils.MatcherUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 10/05/14
 * Time: 9:34 PM
 */
public class ThingControlMinimal {

    private static final Logger myLogger = LoggerFactory
            .getLogger(ThingControl.class);

    protected final PopluateOperator POPULATE_THINGS;

    protected ThingReaders thingReaders = new ThingReaders();
    protected ThingWriters thingWriters = new ThingWriters();
    protected ThingActions thingActions = new ThingActions();
    protected ThingQueries thingQueries = new ThingQueries();

    protected TypeRegistry typeRegistry = new TypeRegistry();

    protected Validator validator = null;

    public ThingControlMinimal() {
        this.POPULATE_THINGS = new PopluateOperator(this);
    }

    @Inject
    public void setTypeRegistry(TypeRegistry typeRegistry) {
        this.typeRegistry = typeRegistry;
    }

    @Inject
    public void setThingReaders(ThingReaders thingReaders) {
        this.thingReaders = thingReaders;
    }

    @Inject
    public void setThingWriters(ThingWriters thingWriters) {
        this.thingWriters = thingWriters;
    }

    @Inject
    public void setThingActions(ThingActions thingActions) {
        this.thingActions = thingActions;
    }

    @Inject
    public void setThingQueries(ThingQueries thingQueries) {
        this.thingQueries = thingQueries;
    }

    public Observable<? extends Thing<?>> observeChilds(Thing t, boolean populate) {

        List<Observable<? extends Thing<?>>> observables = Lists.newLinkedList();
        for (ThingReader r : thingReaders.getAll()) {

            Observable<? extends Thing<?>> child = r.getChildrenForId(t.getId());
            observables.add(child);
        }

        Observable<? extends Thing<?>> obs = Observable.merge(observables);
        if ( populate ) {
            return obs.lift(POPULATE_THINGS);
        } else {
            return obs;
        }
    }

    public List<? extends Thing<?>> findParents(Thing<?> t) {
        return findParents(Observable.just(t));
    }

    public List<? extends Thing<?>> findParents(Observable<? extends Thing<?>> things) {

        return Lists.newArrayList(observeParents(things).toBlockingObservable().toIterable());

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

    public Observable<? extends Thing<?>> observeThingById(String id) {

        List<Observable<? extends Thing<?>>> all = Lists.newLinkedList();

        //TODO make that smarter? maybe cache?
        for ( ThingReader r : thingReaders.getAll() ) {
            all.add(r.findThingForId(id));
        }
        Observable<? extends Thing<?>> obs = Observable.merge(all);

        return obs.first();
    }

    @Inject
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    /**
     * Checks whether the key matches the other key, using key2 matching.
     *
     * @param key  the key
     * @param key2 the other key
     * @return whether one of the matching attemps in either direction is
     * successful
     */
    public static boolean keyMatcheskey(String key, String key2) {

        String key1_type = key.split("/")[0];
        String key1_key = key.split("/")[1];

        String key2_type = key2.split("/")[0];
        String key2_key = key2.split("/")[1];

        boolean type_match = MatcherUtils.wildCardMatch(key1_type, key2_type);
        boolean key_match = MatcherUtils.wildCardMatch(key1_key, key2_key);

        return type_match && key_match;

    }

    public String executeAction(String actionName, Observable<? extends Thing<?>> things, Map<String, String> parameters) throws ActionException {

        ThingAction ta = thingActions.get(actionName);

        if (ta == null) {
            throw new ActionException("Can't find action with name: " + actionName, actionName);
        }

        if ( parameters == null ) {
            parameters = Maps.newHashMap();
        }

        String actionId = ta.execute(actionName, things.lift(POPULATE_THINGS), parameters);

        return actionId;

    }

    public String executeQuery(String actionName, Observable<Thing> things, Map<String, String> parameters) throws ActionException {

        ThingAction ta = thingActions.get(actionName);

        if (ta == null) {
            throw new ActionException("Can't find action with name: " + actionName, actionName);
        }

        if ( parameters == null ) {
            parameters = Maps.newHashMap();
        }

        String actionId = ta.execute(actionName, things.lift(POPULATE_THINGS), parameters);

        return actionId;

    }

    public Thing<?> addChildThingToObservable(Observable<? extends Thing<?>> parents, Thing<?> child) {
        parents.map(p -> addChildThing(p, child)).toBlockingObservable();
        return child;
    }

    public Thing<?> addChildThing(Thing<?> parent, Thing<?> child) {
        ThingWriter w = thingWriters.getUnique(child.getThingType(), child.getKey());
        return w.addChild(parent, child);
    }

    public <V> Thing<V> saveThing(Thing<V> thing) {
        Thing<V> t = thingWriters.getUnique(thing.getThingType(), thing.getKey()).saveThing(thing);
        return t;
    }

    public Observable<? extends Thing<?>> observeChildrenMatchingTypeAndKey(Observable<? extends Thing<?>> t, String typeMatch, String keyMatch, boolean populated) {
        List<Observable<? extends Thing<?>>> observables = Lists.newArrayList();
        List<ThingReader> readers = thingReaders.get(typeMatch, keyMatch);
        for (ThingReader r : readers) {
            Observable<? extends Thing<?>> result = r.getChildrenMatchingTypeAndKey(t, typeMatch, keyMatch);
            observables.add(result);
        }

        Observable<? extends Thing<?>> result = null;
        if (observables.size() == 0) {
            throw new TypeRuntimeException("No connector found for type " + typeRegistry.getThingType(typeMatch) + " and key " + keyMatch, typeMatch);
        } else if (observables.size() == 1) {
            result = observables.get(0);
        } else {
            result = Observable.merge(observables);
        }

        if (populated) {
            return result.lift(POPULATE_THINGS);
        } else {
            return result;
        }

    }

//    public Observable<Thing> findThingForId(String readerName, String id) {
//
//        ThingReader r = thingReaders.getNamed(readerName);
//        Observable<Thing> child = r.findThingForId(id);
//        return child;
//    }

    protected <V> Thing<V> convertToTyped(Class<V> type, Thing untyped) {
        try {
            return (Thing<V>) untyped;
        } catch (ClassCastException cce) {
            throw new TypeRuntimeException("Can't convert to type", type.getClass().toString());
        }
    }

    /**
     * Checks whether a thing with the specified type and key exists.
     *
     * @param typeMatch the type (or type-glob)
     * @param keyMatch  the key (or key-glob)
     * @return whether such a Thing exists
     */
    public boolean thingMatchingTypeAndKeyExists(final String typeMatch, final String keyMatch) {

        Observable<? extends Thing<?>> obs = observeThingsMatchingTypeAndKey(typeMatch, keyMatch, false);
        return !obs.isEmpty().toBlockingObservable().single();
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


    public <V> Observable<Thing<V>> observeCreateThing(String key, V value) {

        Preconditions.checkArgument(key != null, "Can't create thing, no key provided");
        Preconditions.checkArgument(!MatcherUtils.isGlob(key), "Key can't be glob when creating a thing");

        Preconditions.checkArgument(value != null, "Value can't be null");

        Observable<Thing<V>> obs = Observable.just(value).create((Subscriber<? super Thing<V>> subscriber) -> {

            new Thread() {

                public void run() {

                    if (typeRegistry.needsUniqueKey(value))

                    {
                        if (thingMatchingTypeAndKeyExists(typeRegistry.getType(value), key)) {
                            subscriber.onError(
                                    new ThingException(
                                            "There's already a thing stored for key "
                                                    + key
                                                    + " and type "
                                                    + typeRegistry.getType(value)
                                    )
                            );
                            subscriber.onCompleted();
                            return;
                        }
                    }

                    try {
                        validateValue(value);

                        ThingWriter tw = thingWriters.getUnique(typeRegistry.getType(value), key);

                        Thing<V> newThing = new Thing();

                        newThing.setKey(key);
                        newThing.setThingType(typeRegistry.getType(value));
                        newThing.setValue(value);
                        Thing<V> t = saveThing(newThing);

                        subscriber.onNext(t);
                        subscriber.onCompleted();

                    } catch (Exception e) {
                        subscriber.onError(e);
                        subscriber.onCompleted();
                    }
                }
        }.start();

        });

        return obs;
    }
//
//    protected Observable<Thing> ensurePolulatedValueUntyped(Observable<Thing> things) {
//        Observable<Thing> result = things.map(t -> ensurePopulatedValueUntyped(t));
//        return result;
//    }
//
//    protected <V> Observable<Thing<V>> ensurePopluatedValue(Observable<Thing<V>> things) {
//        Observable<Thing<V>> result = things.map(t -> ensurePoplutedValue(t));
//        return result;
//    }



    public <V> Observable<Thing<V>> filterThingsWithValue(Observable<? extends Thing<?>> things, V value) {

        return things.filter(t -> value.equals(getValue(t))).map(t -> (Thing<V>)t);

    }

    public <V> Observable<Thing<V>> observeThingsMatchingKeyAndValue(String key, V value) {

        List<Observable<? extends Thing<?>>> observables = Lists.newArrayList();
        for (ThingReader r : thingReaders.get(typeRegistry.getType(value), key)) {
            Observable<? extends Thing<?>> t = r.findThingsMatchingKey(key);
            observables.add(t);
        }

        Observable<? extends Thing<?>> result = null;
        if (observables.size() == 0) {
            throw new TypeRuntimeException("No connector found for type " + typeRegistry.getType(value) + " and key " + key, typeRegistry.getType(value));
        } else if (observables.size() == 1) {
            result = observables.get(0);
        } else {
            result = Observable.merge(observables);
        }

        return filterThingsWithValue(result, value);
    }

//    protected <V> Observable<V> getValueObservable(final Thing<V> thing) {
//        Observable<V> obs = Observable.create((Observable.OnSubscribe<V>) subscriber -> {
//            ThingReader r = thingReaders.getUnique(thing.getThingType(), thing.getKey());
//            try {
//                subscriber.onNext(r.readValue(thing));
//            } catch (Exception e) {
//                subscriber.onError(e);
//            }
//            subscriber.onCompleted();
//        });
//
//        return obs;
//    }


//    protected Thing ensurePopulatedValueUntyped(Thing thing) {
//        if (thing.getValueIsLink()) {
//            Object value = getValue(thing);
//            thing.setValue(value);
//            thing.setValueIsLink(false);
//        }
//        return thing;
//    }

    protected <V> Thing<V> ensurePopulatedValue(Thing<V> thing) {
        if (thing.getValueIsLink()) {
            V value = getValue(thing);
            thing.setValue(value);
            thing.setValueIsLink(false);
        }
        return thing;
    }

    public <V> V getValue(Thing<V> thing) {
        if ( ! thing.getValueIsLink() ) {
            return (V) thing.getValue();
        }
        ThingReader r = thingReaders.getUnique(thing.getThingType(), thing.getKey());
        return r.readValue(thing);
    }

//    protected Object getValueUntyped(Thing thing) {
//        if ( ! thing.getValueIsLink() ) {
//            return thing.getValue();
//        }
//        ThingReader r = thingReaders.getUnique(thing.getThingType(), thing.getKey());
//        return r.readValue(thing);
//    }

    public <V> Observable<Thing<V>> observeThingsForTypeAndKey(Class<V> typeClass, String key, boolean populate) {

        return observeThingsForTypeAndKey(typeRegistry.getType(typeClass), key, populate)
                .map(t -> convertToTyped(typeClass, t));

    }

    public Observable<? extends Thing<?>> observeThingsForTypeAndKey(String type, String  key, boolean populate) {
        if ( MatcherUtils.isGlob(key) ) {
            throw new ThingRuntimeException("Key can't be glob for this query: "+key);
        }

        if ( MatcherUtils.isGlob(type) ) {
            throw new ThingRuntimeException("Type can't be glob for this query: "+type);
        }

        ThingReader reader = thingReaders.getUnique(type, key);

        return reader.findThingsForTypeAndKey(type, key);

    }

    public Observable<? extends Thing<?>> observeThingsMatchingTypeAndKey(final String typeMatch, final String keyMatch, boolean populate) {
        List<Observable<? extends Thing<?>>> all = Lists.newArrayList();
        List<ThingReader> readers = thingReaders.get(typeMatch, keyMatch);
        if ("*".equals(typeMatch)) {
            if ("*".equals(keyMatch)) {
                for (ThingReader r : readers) {
                    all.add(r.findAllThings());
                }
            } else {
                if (MatcherUtils.isGlob(keyMatch)) {
                    for (ThingReader r : readers) {
                        all.add(r.findThingsMatchingKey(keyMatch));
                    }
                } else {
                    for (ThingReader r : readers) {
                        all.add(r.findThingsForKey(keyMatch));
                    }
                }
            }
        } else if (MatcherUtils.isGlob(typeMatch)) {
            if ("*".equals(keyMatch)) {
                for (ThingReader r : readers) {
                    all.add(r.findThingsMatchingType(typeMatch));
                }
            } else {
                for (ThingReader r : readers) {
                    all.add(r.findThingsMatchingTypeAndKey(typeMatch, keyMatch));
                }
            }
        } else {
            // means type is no glob
            if ("*".equals(keyMatch)) {
                for (ThingReader r : readers) {
                    all.add(r.findThingsForType(typeMatch));
                }
            } else if (MatcherUtils.isGlob(keyMatch)) {
                for (ThingReader r : readers) {
                    all.add(r.findThingsMatchingTypeAndKey(typeMatch, keyMatch));
                }
            } else {
                for (ThingReader r : readers) {
                    all.add(r.findThingsForTypeAndKey(typeMatch, keyMatch));
                }
            }
        }

        Observable<? extends Thing<?>> obs = Observable.merge(all);

        if ( populate ) {
            return obs.lift(POPULATE_THINGS);
        }
        return obs;
    }

    /**
     * Validates the value.
     * <p>
     * Internally, this uses hibernate-validate.
     *
     * @param value the value to validate
     */
    public <V> void validateValue(V value) {

        Set<ConstraintViolation<V>> constraintViolations = validator
                .validate(value);

        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(constraintViolations));
        }
    }
}
