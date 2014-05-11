package things.thing;

import com.google.inject.Singleton;
import rx.Observable;
import things.exceptions.ThingException;
import things.exceptions.ValueException;

import javax.inject.Inject;
import javax.validation.Validator;

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
        Observable<Thing<V>> obs = createThingObservable(key, value);

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
