package things.thing;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;
import things.config.ThingActions;
import things.config.ThingQueries;
import things.config.ThingReaders;
import things.config.ThingWriters;
import things.exceptions.*;
import things.types.TypeRegistry;
import things.utils.MatcherUtils;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.*;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * Minimal implementation of a central class that manages all things related, well..., things...
 *
 * Minimal means there are no utility/convenience methods, and this is the only control class that talks to
 * the registered {@link things.thing.ThingReader}, {@link things.thing.ThingWriter}, {@link things.thing.ThingAction} and
 * {@link things.thing.ThingQuery} classes.
 *
 * In all likelyhood you'll want to use either the {@link things.thing.ThingControlReactive} controller (if you want to
 * use the RxJava {@link rx.Observable}s directly) or, more likely, the full-blown {@link things.thing.ThingControl}
 * implementation, which extends both and additionally provides convenience method to get plain result Lists that block.
 *
 * For more details about the overall workings of this class check out {@link things.thing.ThingControl}.
 */
public class ThingControlMinimal {

    private static final Logger myLogger = LoggerFactory
            .getLogger(ThingControl.class);

    protected final PopluateOperator POPULATE_THINGS;
    private ActionManager actionManager;
    protected Timer create_thing_timer;
    protected MetricRegistry metrics;
    protected ThingActions thingActions = new ThingActions();
    protected ThingQueries thingQueries = new ThingQueries();
    protected ThingReaders thingReaders = new ThingReaders();
    protected ThingWriters thingWriters = new ThingWriters();
    protected TypeRegistry typeRegistry = new TypeRegistry();
    protected Validator validator = null;

    public ThingControlMinimal() {
        this.POPULATE_THINGS = new PopluateOperator(this);
    }

    public <V> Thing<V> addChildThing(Thing<?> parent, Thing<V> child) {

//        if ( child.getParents().contains(parent.getId()) ) {
//            return child;
//        }

        boolean needsUniqueKeyAsChild = typeRegistry.needsUniqueKeyAsChild(child.getThingType());
        boolean needsUniqueKeyAndValueAsChild = typeRegistry.needsUniqueKeyAndValueAsChild(child.getThingType());

        if ( needsUniqueKeyAndValueAsChild || needsUniqueKeyAsChild ) {
            Observable<? extends Thing<?>> childs = observeChildrenForTypeAndKey(parent, child.getThingType(), child.getKey(), false);
            if ( needsUniqueKeyAsChild ) {
                boolean empty = childs.isEmpty().toBlockingObservable().single();
                if ( !empty ) {
                    throw new ThingRuntimeException(parent, "Parent already contains child with type " + child.getThingType() + " and key " + child.getKey());
                }
            } else {
                Object value = getValue(child);
                childs = filterThingsWithValue(childs, value);
                boolean empty = childs.isEmpty().toBlockingObservable().single();
                if ( !empty ) {
                    throw new ThingRuntimeException(parent, "Parent already contains child with type " + child.getThingType() + " and key " + child.getKey() + " and value " + value);
                }
            }

        }

        ThingWriter w = thingWriters.getUnique(child.getThingType(), child.getKey());
        return w.addChild(parent, child);
    }

    public Observable<? extends Thing<?>> executeAction(String actionName, Observable<? extends Thing<?>> things, Map<String, String> parameters) throws ActionException {

        return actionManager.execute(actionName, things, parameters);

    }

    public Observable<? extends Thing<?>> executeQuery(String queryName, Observable<? extends Thing<?>> things, Map<String, String> parameters) {

        Optional<ThingQuery> ta = thingQueries.get(queryName);

        if ( !ta.isPresent() ) {
            throw new QueryRuntimeException("Can't find query with name: " + queryName);
        }

        if ( parameters == null ) {
            parameters = Maps.newHashMap();
        }

        Observable<? extends Thing<?>> result = ta.get().execute(queryName, things.lift(POPULATE_THINGS), parameters);

        return result;

    }

    public <V> Observable<Thing<V>> filterThingsWithValue(Observable<? extends Thing<?>> things, V value) {

        return things.filter(t -> value.equals(getValue(t))).map(t -> (Thing<V>) t);

    }

    public <V> V getValue(Thing<V> thing) {
        if ( !thing.getValueIsPopulated() ) {
            ThingReader r = thingReaders.getUnique(thing.getThingType(), thing.getKey());
            V value = r.readValue(thing);
            thing.setValue(value);
            thing.setValueIsPopulated(true);
            return value;
        }
        return (V) thing.getValue();

    }

    public Observable<? extends Thing<?>> observeAllThings(boolean populateValues) {
        List<Observable<? extends Thing<?>>> all = Lists.newArrayList();
        for ( ThingReader r : thingReaders.getAll() ) {
            all.add(r.findAllThings());
        }

        Observable<? extends Thing<?>> obs = Observable.merge(all);
        if ( populateValues ) {
            return obs.lift(POPULATE_THINGS);
        }
        return obs;
    }

    public Observable<? extends Thing<?>> observeChildren(Thing<?> t, boolean populate) {

        List<Observable<? extends Thing<?>>> observables = Lists.newLinkedList();
        for ( ThingReader r : thingReaders.getAll() ) {

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

    public Observable<? extends Thing<?>> observeChildren(Observable<? extends Thing<?>> things, boolean populate) {

        return things.flatMap(t -> observeChildren(t, populate));
    }

    public Observable<? extends Thing<?>> observeChildrenForTypeAndKey(Thing<?> t, String type, String key, boolean populated) {
        return observeChildrenForTypeAndKey(Observable.just(t), type, key, populated);
    }

    public Observable<? extends Thing<?>> observeChildrenForTypeAndKey(Observable<? extends Thing<?>> t, String type, String key, boolean populated) {

        if ( MatcherUtils.isGlob(key) ) {
            throw new ThingRuntimeException("Key can't be glob for this query: " + key);
        }

        if ( MatcherUtils.isGlob(type) ) {
            throw new ThingRuntimeException("Type can't be glob for this query: " + type);
        }

        ThingReader tr = thingReaders.getUnique(type, key);
        return tr.getChildrenMatchingTypeAndKey(t, type, key);
    }

    public Observable<? extends Thing<?>> observeChildrenMatchingTypeAndKey(Observable<? extends Thing<?>> t, String typeMatch, String keyMatch, boolean populated) {

        List<Observable<? extends Thing<?>>> observables = Lists.newLinkedList();
        Set<ThingReader> readers = thingReaders.get(typeMatch, keyMatch);
        if ( "*".equals(typeMatch) ) {
            if ( "*".equals(keyMatch) ) {
                return observeChildren(t, populated);

            } else {
                if ( MatcherUtils.isGlob(keyMatch) ) {
                    for ( ThingReader r : readers ) {
                        observables.add(r.getChildrenMatchingKey(t, keyMatch));
                    }
                } else {
                    for ( ThingReader r : readers ) {
                        observables.add(r.getChildrenForKey(t, keyMatch));
                    }
                }
            }
        } else if ( MatcherUtils.isGlob(typeMatch) ) {
            if ( "*".equals(keyMatch) ) {
                for ( ThingReader r : readers ) {
                    observables.add(r.getChildrenMatchingType(t, typeMatch));
                }
            } else {
                for ( ThingReader r : readers ) {
                    observables.add(r.getChildrenMatchingTypeAndKey(t, typeMatch, keyMatch));
                }
            }
        } else {
            // means type is no glob
            if ( "*".equals(keyMatch) ) {
                for ( ThingReader r : readers ) {
                    observables.add(r.getChildrenForType(t, typeMatch));
                }
            } else if ( MatcherUtils.isGlob(keyMatch) ) {
                for ( ThingReader r : readers ) {
                    observables.add(r.getChildrenMatchingTypeAndKey(t, typeMatch, keyMatch));
                }
            } else {
                return observeChildrenForTypeAndKey(t, typeMatch, keyMatch, populated);
            }
        }


        Observable<? extends Thing<?>> result = null;
        if ( observables.size() == 0 ) {
            throw new TypeRuntimeException("No connector found for type " + typeRegistry.getThingType(typeMatch) + " and key " + keyMatch, typeMatch);
        } else if ( observables.size() == 1 ) {
            result = observables.get(0);
        } else {
            result = Observable.merge(observables);
        }

        if ( populated ) {
            return result.lift(POPULATE_THINGS);
        } else {
            return result;
        }

    }

    public <V> Observable<Thing<V>> observeCreateThing(String key, V value) {

        Preconditions.checkArgument(key != null, "Can't create thing, no key provided");
        Preconditions.checkArgument(!MatcherUtils.isGlob(key), "Key can't be glob when creating a thing");

        Preconditions.checkArgument(value != null, "Value can't be null");

        Observable<Thing<V>> obs = Observable.just(value).create((Subscriber<? super Thing<V>> subscriber) -> {

            new Thread() {

                public void run() {

                    if ( typeRegistry.needsUniqueKey(value) ) {
                        if ( thingMatchingTypeAndKeyExists(typeRegistry.getType(value), key) ) {
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

                    if ( typeRegistry.needsUniqueValueForKey(value) ) {
                        Observable<? extends Thing<?>> obs = observeThingsMatchingKeyAndValue(key, value);
                        if ( !obs.isEmpty().toBlockingObservable().single() ) {
                            subscriber.onError(
                                    new ThingException(
                                            "There's already a thing stored for key "
                                                    + key
                                                    + " and value "
                                                    + value
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
                        newThing.setValueIsPopulated(true);
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

    public Observable<? extends Thing<?>> observeThingById(String id) {

        List<Observable<? extends Thing<?>>> all = Lists.newLinkedList();

        //TODO make that smarter? maybe cache?
        for ( ThingReader r : thingReaders.getAll() ) {
            all.add(r.findThingForId(id));
        }
        Observable<? extends Thing<?>> obs = Observable.merge(all);

        //TODO maybe check for single? Although, first might be quicker.
        return obs.first();
    }

    public Observable<? extends Thing<?>> observeThingsForTypeAndKey(String type, String key, boolean populate) {

        if ( MatcherUtils.isGlob(key) ) {
            throw new ThingRuntimeException("Key can't be glob for this query: " + key);
        }

        if ( MatcherUtils.isGlob(type) ) {
            throw new ThingRuntimeException("Type can't be glob for this query: " + type);
        }

        ThingReader reader = thingReaders.getUnique(type, key);

        return reader.findThingsForTypeAndKey(type, key);

    }

    public <V> Observable<Thing<V>> observeThingsMatchingKeyAndValue(String keyMatcher, V value) {


        Set<ThingReader> readers = thingReaders.get(typeRegistry.getType(value), keyMatcher);

        List<Observable<? extends Thing<?>>> observables = Lists.newArrayList();

        for ( ThingReader r : readers ) {
            Observable<? extends Thing<?>> obs = r.findThingsMatchingKeyAndValue(keyMatcher, value);
            observables.add(obs);
        }

        Observable<? extends Thing<?>> result = null;
        if ( observables.size() == 0 ) {
            throw new TypeRuntimeException("No reader found for type " + typeRegistry.getType(value) + " and key " + keyMatcher, typeRegistry.getType(value));
        } else if ( observables.size() == 1 ) {
            result = observables.get(0);
        } else {
            result = Observable.merge(observables);
        }

        return result.map(t -> populateAndConvertToTyped((Class<V>) value.getClass(), t));

    }

    public Observable<? extends Thing<?>> observeThingsMatchingKeyAndValueConvertedFromString(String type, String keyMatcher, String valueString) {
        if ( MatcherUtils.isGlob(type) ) {
            throw new ThingRuntimeException("Type can't be glob for this query");
        }
        return observeThingsMatchingKeyAndValueConvertedFromString(typeRegistry.getTypeClass(type), keyMatcher, valueString);
    }

    public <V> Observable<Thing<V>> observeThingsMatchingKeyAndValueConvertedFromString(Class<V> typeClass, String keyMatcher, String valueString) {

        String type = typeRegistry.getType(typeClass);

        if ( !typeRegistry.convertsFromString(type) ) {
            throw new TypeRuntimeException("Can't convert value for type '" + type + "' from String", type);
        }

        Optional<?> value = typeRegistry.convertFromString(type, valueString);
        if ( !value.isPresent() ) {
            throw new TypeRuntimeException("Could not convert type '" + type + "' from String", type);
        }

        return observeThingsMatchingKeyAndValue(keyMatcher, (V) value.get());

    }

    public Observable<? extends Thing<?>> observeThingsMatchingTypeAndKey(final String typeMatch, final String keyMatch, boolean populate) {

        List<Observable<? extends Thing<?>>> all = Lists.newLinkedList();
        Set<ThingReader> readers;
        try {
            readers = thingReaders.get(typeMatch, keyMatch);
        } catch (TypeRuntimeException tre) {
            myLogger.debug("No connector found for type '{}' and key '{}", typeMatch, keyMatch, tre);
            return Observable.empty();
        }
        if ( "*".equals(typeMatch) ) {
            if ( "*".equals(keyMatch) ) {
                for ( ThingReader r : readers ) {
                    all.add(r.findAllThings());
                }
            } else {
                if ( MatcherUtils.isGlob(keyMatch) ) {
                    for ( ThingReader r : readers ) {
                        all.add(r.findThingsMatchingKey(keyMatch));
                    }
                } else {
                    for ( ThingReader r : readers ) {
                        all.add(r.findThingsForKey(keyMatch));
                    }
                }
            }
        } else if ( MatcherUtils.isGlob(typeMatch) ) {
            if ( "*".equals(keyMatch) ) {
                for ( ThingReader r : readers ) {
                    all.add(r.findThingsMatchingType(typeMatch));
                }
            } else {
                for ( ThingReader r : readers ) {
                    all.add(r.findThingsMatchingTypeAndKey(typeMatch, keyMatch));
                }
            }
        } else {
            // means type is no glob
            if ( "*".equals(keyMatch) ) {
                for ( ThingReader r : readers ) {
                    all.add(r.findThingsForType(typeMatch));
                }
            } else if ( MatcherUtils.isGlob(keyMatch) ) {
                for ( ThingReader r : readers ) {
                    all.add(r.findThingsMatchingTypeAndKey(typeMatch, keyMatch));
                }
            } else {
                for ( ThingReader r : readers ) {
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

    protected <V> Thing<V> populateAndConvertToTyped(Class<V> type, Thing untyped) {
        POPULATE_THINGS.populate(untyped);

        Thing<V> temp = (Thing<V>) untyped;
        // make sure the value is of the right type;
        Class expectedTypeClass = typeRegistry.getTypeClass(temp.getThingType());

        if ( !temp.getValue().getClass().equals(type) ) {
            throw new TypeRuntimeException("Can't convert to type: " + temp.getThingType(), temp.getThingType());
        }

        return temp;

    }

    /**
     * Saves or updates this thing.
     *
     * Make sure you use the return-Thing after saving, because depending on the underlying storage
     * backend ({@link things.thing.ThingWriter}), it might be that the old Thing is out of date (session-handling, etc...)
     *
     * @param thing the thing
     * @return the persistent thing
     * @throws ThingException if more than one {@link things.thing.ThingWriter} was configured for the type/key combination of this thing
     */
    public <V> Thing<V> saveThing(Thing<V> thing) throws ThingException {

        if ( MatcherUtils.isGlob(thing.getThingType()) ) {
            throw new ThingException(thing, "Type can't be glob");
        }
        if ( MatcherUtils.isGlob(thing.getKey()) ) {
            throw new ThingException(thing, "Key can't be glob");
        }
        Thing<V> t = thingWriters.getUnique(thing.getThingType(), thing.getKey()).saveThing(thing);
        if ( Strings.isNullOrEmpty(t.getId()) ) {
            throw new ThingWriterRuntimeException("Thing id can't be empty after save");
        }
        return t;
    }

    @Inject
    public void setActionManager(ActionManager am) {
        this.actionManager = am;
    }

    @Inject
    public void setMetrics(MetricRegistry metrics) {
        this.metrics = metrics;
        create_thing_timer = metrics.timer(name(ThingControlMinimal.class, "create-thing"));

    }

    @Inject
    public void setThingActions(ThingActions thingActions) {
        this.thingActions = thingActions;
    }

    @Inject
    public void setThingQueries(ThingQueries thingQueries) {
        this.thingQueries = thingQueries;
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
    public void setTypeRegistry(TypeRegistry typeRegistry) {
        this.typeRegistry = typeRegistry;
    }

    @Inject
    public void setValidator(Validator validator) {
        this.validator = validator;
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
     * Validates the value.
     *
     * Internally, this uses hibernate-validate.
     *
     * @param value the value to validate
     */
    public <V> void validateValue(V value) {

        Set<ConstraintViolation<V>> constraintViolations = validator
                .validate(value);

        if ( constraintViolations.size() > 0 ) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(constraintViolations));
        }
    }
}
