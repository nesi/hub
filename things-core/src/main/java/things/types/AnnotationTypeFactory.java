package things.types;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Sets;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import things.exceptions.TypeRuntimeException;
import things.model.types.Value;
import things.model.types.attributes.*;

import java.util.Optional;
import java.util.Set;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 15/05/14
 * Time: 10:12 PM
 */
public class AnnotationTypeFactory {

    private static final Logger myLogger = LoggerFactory.getLogger(AnnotationTypeFactory.class);

    private static Reflections reflections = null;
    private static Set<ThingType<?>> thingTypes = null;
    private static ImmutableBiMap<String, Class> typeMap;
    private static Set<Class<?>> types;

    public synchronized static Set<ThingType<?>> getAllTypes() {
        if ( thingTypes == null ) {
            thingTypes = Sets.newHashSet();
            for ( String type : getTypeMap().keySet() ) {
                Class typeClass = getTypeMap().get(type);
                ThingType tt = new ThingType(type, typeClass);
                tt.setNeedsUniqueKey(typeNeedsUniqueKey(typeClass));
                tt.setNeedsUniqueKeyAsChild(typeNeedsUniqueKeyWithinChildren(typeClass));
                tt.setNeedsUniqueValue(typeNeedsUniqueValue(typeClass));
                tt.setNeedsUniqueValueForKeyAsChild(typeNeedsUniqueKeyAndValueWithinChildren(typeClass));
                Optional<SingleStringConverter> conv = getStringConverter(typeClass);
                if ( conv.isPresent() ) {
                    tt.setConverter(conv.get());
                }
                thingTypes.add(tt);
            }
        }
        return thingTypes;
    }

    public static Optional<SingleStringConverter> getStringConverter(Class<?> typeClass) {

        if ( typeClass == null ) {
            throw new TypeRuntimeException("No typeClass provided");
        }

        StringConverter annotation = typeClass.getAnnotation(StringConverter.class);
        if ( annotation == null || annotation.value() == null ) {
            return Optional.empty();
        } else {
            Class<? extends SingleStringConverter<?>> converterClass = annotation.value();
            try {
                SingleStringConverter conv = converterClass.newInstance();
                return Optional.of(conv);
            } catch (Exception e) {
                throw new TypeRuntimeException("Can't create stringconverter for class '" + typeClass.getName() + "'", typeClass, e);
            }
        }
    }

    /**
     * Checks if a value of this type has restrictions as to which types it can
     * be added to.
     * <p>
     * This is determined by looking up the {@link things.model.types.attributes.Subordinate} annotation.
     * If no such annotation is present, null is returned.
     *
     * @param typeClass the type class
     * @return the only parent class a thing of this type can be added to or null if no such class exists
     */
    public static Class<?> getSubordinateParentClass(Class<?> typeClass) {
        Subordinate annotation = typeClass.getAnnotation(Subordinate.class);
        if ( annotation == null ) {
            return null;
        } else {
            return annotation.parentClass();
        }
    }

    public static BiMap<String, Class> getTypeMap() {
        if ( typeMap == null ) {
            types = singleton().getTypesAnnotatedWith(Value.class);
            HashBiMap<String, Class> temp = HashBiMap.create();
            for ( Class type : types ) {
                Value ann = (Value) type.getAnnotation(Value.class);
                String key;
                if ( ann == null ) {
                    String name = type.getSimpleName();
                    key = Character.toLowerCase(name.charAt(0)) + name.substring(1);
                    myLogger.info("No annotation found for type: {}, using: " + key, type);
                } else {
                    key = ann.typeName();
                }
                temp.put(key, type);
            }

            typeMap = ImmutableBiMap.copyOf(temp);
        }
        return typeMap;
    }

    public static Reflections singleton() {

        if ( reflections == null ) {
            reflections = new Reflections();
        }

        return reflections;
    }

    /**
     * Checks whether values of this type need to have a unique key.
     * <p>
     * To determine this, the {@link things.model.types.attributes.UniqueKey} annotation is used.
     * If the annotation is not present, false is returned.
     *
     * @param typeClass the type
     * @return whether values of this type need a unique key
     */
    public static boolean typeNeedsUniqueKey(Class<?> typeClass) {

        if ( typeClass == null ) {
            throw new TypeRuntimeException("No typeClass provided");
        }

        UniqueKey annotation = typeClass.getAnnotation(UniqueKey.class);
        if ( annotation == null || annotation.unique() == false ) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks whether values of this type need to have a unique key when being added to another Thing.
     * <p>
     * To determine this, the {@link things.model.types.attributes.UniqueKeyAsChild} annotation is used.
     * If the annotation is not present, false is returned.
     *
     * @param typeClass the type
     * @return whether values of this type need a unique key
     */
    public static boolean typeNeedsUniqueKeyWithinChildren(Class<?> typeClass) {

        if ( typeClass == null ) {
            throw new TypeRuntimeException("No typeClass provided");
        }

        UniqueKeyAsChild annotation = typeClass.getAnnotation(UniqueKeyAsChild.class);
        if ( annotation == null || annotation.unique() == false ) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean typeNeedsUniqueKeyAndValueWithinChildren(Class<?> typeClass) {

        if ( typeClass == null ) {
            throw new TypeRuntimeException("No typeClass provided");
        }

        UniqueValueForKeyAsChild annotation = typeClass.getAnnotation(UniqueValueForKeyAsChild.class);
        if ( annotation == null || annotation.unique() == false ) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks whether values of this type need to have a unique value for this type.
     * <p>
     * To determine this, the {@link things.model.types.attributes.UniqueValueForKey} annotation is used.
     * If the annotation is not present, false is returned.
     * <p>
     * Be careful using the {@link things.model.types.attributes.UniqueValueForKey} annotation, since it can slow down
     * persisting things of this type due to checking whether the value already exists.
     *
     * @param typeClass the type
     * @return whether values of this type need a unique value
     */
    public static boolean typeNeedsUniqueValue(Class<?> typeClass) {

        if ( typeClass == null ) {
            throw new TypeRuntimeException("No typeClass provided");
        }

        UniqueValueForKey annotation = typeClass.getAnnotation(UniqueValueForKey.class);
        if ( annotation == null || annotation.unique() == false ) {
            return false;
        } else {
            return true;
        }
    }

}
