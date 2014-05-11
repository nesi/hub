package things.thing;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;
import things.exceptions.ThingException;
import things.exceptions.TypeRuntimeException;
import things.utils.MatcherUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    protected final ThingReaders thingReaders;
    protected final ThingWriters thingWriters;
    protected Validator validator;

    public ThingControlMinimal(ThingReaders thingReaders, ThingWriters thingWriters, Validator validator) {
        this.thingReaders = thingReaders;
        this.thingWriters = thingWriters;
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

    public Thing addChildThing(Thing parent, Thing child) {
        ThingReader r = thingReaders.getUnique(child);
        parent.getOtherThings().add(r.getReaderName()+"/"+child.getId());
        return saveThing(parent);
    }

    public <V> Thing<V> saveThing(Thing<V> thing) {
        Thing<V> t = thingWriters.getUnique(thing.getThingType(), thing.getKey()).saveThing(thing);
        return t;
    }

    public Observable<Thing> observeChildsMatchingTypeAndKey(Thing t, String typeMatch, String keyMatch, boolean populated) {
        List<Observable<Thing>> observables = Lists.newArrayList();
        List<ThingReader> readers = thingReaders.get(typeMatch, keyMatch);
        for (ThingReader r : readers) {
            Observable<Thing> result = r.getChildsMatchingTypeAndKey(t, typeMatch, keyMatch);
            observables.add(result);
        }

        Observable<Thing> result = null;
        if (observables.size() == 0) {
            throw new TypeRuntimeException("No connector found for type " + TypeRegistry.getType(typeMatch) + " and key " + keyMatch, typeMatch);
        } else if (observables.size() == 1) {
            result = observables.get(0);
        } else {
            result = Observable.merge(observables);
        }

        if (populated) {
            return ensurePolulatedValueUntyped(result);
        } else {
            return result;
        }

    }

    public Observable<Thing> findThingForId(String readerName, String id) {

        ThingReader r = thingReaders.getNamed(readerName);
        Observable<Thing> child = r.findThingForId(id);
        return child;
    }

    protected <V> Thing<V> convertToTyped(Class<V> type, Thing untyped) {
        try {
            return (Thing<V>) untyped;
        } catch (ClassCastException cce) {
            throw new TypeRuntimeException("Can't convert to type", TypeRegistry.getType(type));
        }
    }

    /**
     * Checks whether a thing with the specified type and key exists.
     *
     * @param typeMatch the type (or type-glob)
     * @param keyMatch  the key (or key-glob)
     * @return whether such a Thing exists
     */
    public boolean thingWithTypeAndKeyExists(final String typeMatch, final String keyMatch) {

        Observable<Thing> obs = observeThingsMatchingTypeAndKey(typeMatch, keyMatch, false);
        return !obs.isEmpty().toBlockingObservable().single();
    }


    protected <V> Observable<Thing<V>> observeCreateThing(String key, V value) {

        Preconditions.checkArgument(key != null, "Can't create thing, no key provided");
        Preconditions.checkArgument(!MatcherUtils.isGlob(key), "Key can't be glob when creating a thing");

        Preconditions.checkArgument(value != null, "Value can't be null");

        Observable<Thing<V>> obs = Observable.just(value).create((Subscriber<? super Thing<V>> subscriber) -> {

            new Thread(() -> {

                if (TypeRegistry.valueNeedsUniqueKey(value
                        .getClass()))

                {
                    if (thingWithTypeAndKeyExists(TypeRegistry.getType(value), key)) {
                        subscriber.onError(
                                new ThingException(
                                        "There's already a thing stored for key "
                                                + key
                                                + " and type "
                                                + TypeRegistry.getType(value
                                                .getClass())
                                )
                        );
                    }
                }

                try {
                    validateValue(value);

                    ThingWriter tw = thingWriters.getUnique(TypeRegistry.getType(value), key);

                    Thing<V> newThing = new Thing();

                    Object valueId = null;
                    if (TypeRegistry.isSimpleValue(value)) {
                        valueId = value;
                        newThing.setValue(valueId);
                        newThing.setValueIsLink(false);
                    } else {
                        valueId = tw.saveValue(Optional.empty(), value);
                        newThing.setValue(valueId);
                        newThing.setValueIsLink(true);
                    }

                    String type = TypeRegistry.getType(value);

                    newThing.setKey(key);
                    newThing.setThingType(type);

                    Thing<V> t = saveThing(newThing);

                    subscriber.onNext(t);
                    subscriber.onCompleted();

                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }).start();

        });

        return obs;
    }

    protected Observable<Thing> ensurePolulatedValueUntyped(Observable<Thing> things) {
        Observable<Thing> result = things.map(t -> ensurePopulatedValueUntyped(t));
        return result;
    }

    protected <V> Observable<Thing<V>> ensurePopluatedValue(Observable<Thing<V>> things) {
        Observable<Thing<V>> result = things.map(t -> ensurePoplutedValue(t));
        return result;
    }


    public <V> Observable<Thing<V>> observeThingsWithKeyAndValue(String key, V value, boolean populated) {

        List<Observable<Thing<V>>> observables = Lists.newArrayList();
        for (ThingReader r : thingReaders.get(TypeRegistry.getType(value.getClass()), key)) {
            Observable<Thing<V>> t = r.findThingsByKeyAndValue(key, value);
            observables.add(t);
        }

        Observable<Thing<V>> result = null;
        if (observables.size() == 0) {
            throw new TypeRuntimeException("No connector found for type " + TypeRegistry.getType(value) + " and key " + key, TypeRegistry.getType(value));
        } else if (observables.size() == 1) {
            result = observables.get(0);
        } else {
            result = Observable.merge(observables);
        }
        if (populated) {
            return ensurePopluatedValue(result);
        } else {
            return result;
        }
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


    protected Thing ensurePopulatedValueUntyped(Thing thing) {
        if (thing.getValueIsLink()) {
            Object value = getValue(thing);
            thing.setValue(value);
            thing.setValueIsLink(false);
        }
        return thing;
    }

    protected <V> Thing<V> ensurePoplutedValue(Thing<V> thing) {
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

    public Observable<Thing> observeThingsMatchingTypeAndKey(final String typeMatch, final String keyMatch, boolean populate) {
        List<Observable<Thing>> all = Lists.newArrayList();
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

        Observable<Thing> obs = Observable.merge(all);

        if ( populate ) {
            return ensurePolulatedValueUntyped(obs);
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
