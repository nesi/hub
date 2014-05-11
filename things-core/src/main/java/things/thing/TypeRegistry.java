package things.thing;

import com.google.common.base.Strings;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import things.exceptions.TypeRuntimeException;
import things.model.types.SimpleValue;
import things.model.types.Value;
import things.model.types.attributes.Subordinate;
import things.model.types.attributes.UniqueKey;
import things.model.types.attributes.UniqueKeyInOtherThings;
import things.model.types.attributes.UniqueValueForKey;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * The *TypeRegistry* performs simple lookups related to Types and their properties,
 * it also contains a few convenience methods to determine equalities and such.
 *
 * @author Markus Binsteiner
 */
public class TypeRegistry {

    private static final Logger myLogger = LoggerFactory.getLogger(TypeRegistry.class);

    private static Reflections reflections = null;
    private static ImmutableBiMap<String, Class> typeMap;
    private static Set<Class<?>> types;

    public static Reflections singleton() {

        if (reflections == null) {
            reflections = new Reflections();
        }

        return reflections;
    }

    /**
     * Whether both supplied parameters have the same type.
     *
     * @param type  the first type
     * @param other the second type
     * @return true if both types are non-null and reflect the same type
     */
    public static boolean equalsType(String type, String other) {
        if (type == null || other == null) {
            return false;
        }
        return Objects.equals(type, other);
    }

    /**
     * Whether both supplied parameters have the same type.
     *
     * @param type  the first type
     * @param other the second type
     * @return true if both types are non-null and reflect the same type
     */
    public static boolean equalsType(String type, Class<?> other) {
        if (type == null || other == null) {
            return false;
        }
        return equalsType(type, getType(other));
    }

    /**
     * Whether both supplied parameters have the same type.
     *
     * @param type  the first type
     * @param other the second type
     * @return true if both types are non-null and reflect the same type
     */
    public static boolean equalsType(Class<?> type, Class<?> other) {
        if (type == null || other == null) {
            return false;
        }
        return Objects.equals(type, other);
    }

    /**
     * Whether both supplied parameters have the same type.
     *
     * @param type  the first type
     * @param other the second type
     * @return true if both types are non-null and reflect the same type
     */
    public static boolean equalsType(Class<?> type, String other) {
        if (type == null || other == null) {
            return false;
        }
        return equalsType(getType(type), other);
    }

    /**
     * The typeMap is a bidirectional map that can be used to lookup classes and their
     * associated type strings.
     * <p>
     * In most cases, the type string is the name of the class, lowercased.
     *
     * @return the typeMap
     */
    public static BiMap<String, Class> getTypeMap() {
        if (typeMap == null) {
            types = singleton().getTypesAnnotatedWith(Value.class);
            HashBiMap<String, Class> temp = HashBiMap.create();
            for (Class type : types) {
                Value ann = (Value) type.getAnnotation(Value.class);
                String key;
                if (ann == null) {
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

    /**
     * Looks up the type string of the supplied class.
     *
     * @param typeClass the type class
     * @return the Type String (or null, if no type could be found for this class)
     */
    public static String getType(Class typeClass) {

        String type = getTypeMap().inverse().get(typeClass);
        if (Strings.isNullOrEmpty(type)) {
            type = typeClass.getSimpleName();
            type = Character.toLowerCase(type.charAt(0)) + type.substring(1);
        }
        return type;
    }


    /**
     * Looks up the type class of the supplied type string.
     *
     * @param type the type String.
     * @return the type class (or null if no type class could be found for this String)
     */
    public static Class<?> getTypeClass(String type) {
        Class<? extends Serializable> typeclass = getTypeMap().get(type);
        if (typeclass == null) {
            throw new TypeRuntimeException("Can't find class for type: " + type, type);
        }
        return typeclass;
    }

    /**
     * Checks whether values of this type need to have a unique key.
     * <p>
     * To determine this, the {@link things.model.types.attributes.UniqueKey} annotation is used.
     * If the annotation is not present, false is returned.
     *
     * @param type the type
     * @return whether values of this type need a unique key
     */
    public static boolean valueNeedsUniqueKey(String type) {
        return valueNeedsUniqueKey(TypeRegistry.getTypeClass(type));
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
    public static boolean valueNeedsUniqueKey(Class<?> typeClass) {

        if (typeClass == null) {
            throw new TypeRuntimeException("No typeClass provided", null);
        }

        UniqueKey annotation = typeClass.getAnnotation(UniqueKey.class);
        if (annotation == null || annotation.unique() == false) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks whether values of this type need to have a unique key when being added to another Thing.
     * <p>
     * To determine this, the {@link things.model.types.attributes.UniqueKeyInOtherThings} annotation is used.
     * If the annotation is not present, false is returned.
     *
     * @param type the type
     * @return whether values of this type need a unique key
     */
    public static boolean valueNeedsUniqueKeyInOtherThings(String type) {
        return valueNeedsUniqueKeyInOtherThings(TypeRegistry.getTypeClass(type));
    }

    /**
     * Checks whether values of this type need to have a unique key when being added to another Thing.
     * <p>
     * To determine this, the {@link things.model.types.attributes.UniqueKeyInOtherThings} annotation is used.
     * If the annotation is not present, false is returned.
     *
     * @param typeClass the type
     * @return whether values of this type need a unique key
     */
    public static boolean valueNeedsUniqueKeyInOtherThings(Class<?> typeClass) {

        if (typeClass == null) {
            throw new TypeRuntimeException("No typeClass provided", null);
        }

        UniqueKeyInOtherThings annotation = typeClass.getAnnotation(UniqueKeyInOtherThings.class);
        if (annotation == null || annotation.unique() == false) {
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

        if (typeClass == null) {
            throw new TypeRuntimeException("No typeClass provided", null);
        }

        UniqueValueForKey annotation = typeClass.getAnnotation(UniqueValueForKey.class);
        if (annotation == null || annotation.unique() == false) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if a value of this type has restrictions as to which types it can
     * be added to.
     * <p>
     * This is determined by looking up the {@link things.model.types.attributes.Subordinate} annotation.
     * If no such annotation is present, null is returned.
     *
     * @param t a Thing
     * @return the only parent class this thing can be added to or null if no such class exists
     */
    public static Class<?> getSubordinateParentClass(Thing t) {
        return getSubordinateParentClass(getTypeClass(t.getThingType()));
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
        if (annotation == null) {
            return null;
        } else {
            return annotation.parentClass();
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
     * @return the only parent type a thing of this type can be added to or null if no such type exists
     */
    public static String getSubordinateParentType(Class<?> typeClass) {

        if (typeClass == null) {
            throw new TypeRuntimeException("No typeClass provided", null);
        }

        Class<?> temp = getSubordinateParentClass(typeClass);
        if (temp == null) {
            return null;
        } else {
            return getType(temp);
        }
    }

    /**
     * Checks if a value of this type has restrictions as to which types it can
     * be added to.
     * <p>
     * This is determined by looking up the {@link things.model.types.attributes.Subordinate} annotation.
     * If no such annotation is present, null is returned.
     *
     * @param child the type class
     * @return the only parent type a thing of this type can be added to or null if no such type exists
     */
    public static String getSubordinateParentType(Thing child) {
        return getSubordinateParentType(child);
    }

    /**
     * Checks if a value of this type has restrictions as to which types it can
     * be added to.
     * <p>
     * This is determined by looking up the {@link things.model.types.attributes.Subordinate} annotation.
     * If no such annotation is present, null is returned.
     *
     * @param type the type class
     * @return the only parent type a thing of this type can be added to or null if no such type exists
     */
    private static String getSubordinateParentType(String type) {
        return getSubordinateParentType(getTypeClass(type));
    }

    /**
     * Returns the Type String for this value.
     *
     * @param value the value
     * @return the type string
     */
    public static String getType(Object value) {
        return getType(value.getClass());
    }

    /**
     * Checks whether this type is a single-string-value.
     * <p>
     * This is determined by looking up the types' class interfaces. If the
     * {@link things.model.SingleStringValue} interface is implemented, true is returned.
     *
     * @param type the type
     * @return whether this type implements {@link things.model.SingleStringValue}
     */
    public static boolean isSimpleValue(String type) {
        return isSimpleValue(getTypeClass(type));
    }

    public static boolean isSimpleValue(Object value) {
        return isSimpleValue(value.getClass());
    }

    /**
     * Checks whether this type is a single-string-value.
     * <p>
     * This is determined by looking up the types' class interfaces. If the
     * {@link things.model.SingleStringValue} interface is implemented, true is returned.
     *
     * @param typeClass the type
     * @return whether this type implements {@link things.model.SingleStringValue}
     */
    public static boolean isSimpleValue(Class<?> typeClass) {
        return SimpleValue.class.isAssignableFrom(typeClass);
    }

    /**
     * Assembles a Value object out of the provided value string.
     * <p>
     * This only works for single-string-types (i.e. types that implement {@link things.model.SingleStringValue}).
     *
     * @param type  the type
     * @param value the string value
     * @return the assembled value
     */
    public static <T> SimpleValue<T> createSimpleValue(String type, T value) {

        Class<?> typeClass = getTypeClass(type);

        if (!isSimpleValue(typeClass)) {
            throw new TypeRuntimeException("Not SimpleValue type: " + type, type);
        }

        return createSimpleValue((Class<? extends SimpleValue<T>>) typeClass, value);

    }

    public static <T> SimpleValue createSimpleValue(Class<? extends SimpleValue<T>> typeClass, T value) {

        try {
            SimpleValue<T> newValue = typeClass.newInstance();
            newValue.setValue(value);
            return newValue;
        } catch (Exception e) {
            throw new TypeRuntimeException("Can't create SingleStringType: " + e.getLocalizedMessage(), TypeRegistry.getType(typeClass), e);
        }

    }


}
