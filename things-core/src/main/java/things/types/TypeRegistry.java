package things.types;

import com.google.common.collect.Maps;
import things.exceptions.NoSuchTypeException;

import java.util.*;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 14/05/14
 * Time: 8:43 PM
 */
public class TypeRegistry {

    private final TreeMap<String, ThingType<?>> types = Maps.newTreeMap();
    private final Map<Class, ThingType<?>> typeClasses = Maps.newHashMap();

    public TypeRegistry() {
    }

    public TypeRegistry(Collection<? extends ThingType<?>> types) {
        for ( ThingType<?> type : types ) {
            addType(type);
        }
    }

    public void addType(ThingType<?> type) {
        types.put(type.getType(), type);
        typeClasses.put(type.getTypeClass(), type);
    }

    public ThingType getThingType(Class<?> type) {
        if ( typeClasses.get(type) == null ) {
            return typeClasses.get(type);
        } else {
            throw new NoSuchTypeException("Can't find typeClass for class: "+type.toString());
        }
    }

    public ThingType getThingType(String type) {
        if ( types.get(type) == null ) {
            return types.get(type);
        } else {
            throw new NoSuchTypeException("Can't find typeClass: " + type);
        }
    }

    public ThingType getThingType(Object value) {
        return getThingType(value.getClass());
    }

    public String getType(Class<?> typeClass) {
        return getThingType(typeClass).getType();
    }

    public String getType(Object value) {
        return getThingType(value).getType();
    }

    public Class<?> getTypeClass(String type) {
        return getThingType(type).getTypeClass();
    }

    public Class<?> getTypeClass(Object value) {
        return getThingType(value).getTypeClass();
    }

    public Collection<ThingType<?>> getAllThingTypes() {
        return types.values();
    }

    public Set<String> getAllTypes() {
        return types.keySet();
    }

    public Optional<String> convertToString(Object value) {
        return getThingType(value).convertToString(value);

    }

    public Optional<? extends Object> convertFromString(String type, String valueString) {
        return getThingType(type).convertFromString(valueString);
    }

    public boolean needsUniqueKey(Object value) {
        return getThingType(value).isNeedsUniqueKey();
    }

    public boolean needsUniqueKey(Class typeClass) {
        return getThingType(typeClass).isNeedsUniqueKey();
    }

    public boolean needsUniqueKey(String type) {
        return getThingType(type).isNeedsUniqueKey();
    }

    public  Boolean equals(Class typeClass, String thingType) {
        return getThingType(typeClass).equals(getThingType(thingType));
    }

    public  Boolean equals(String thingType, Class typeClass) {
        return getThingType(typeClass).equals(getThingType(thingType));
    }

    public Boolean equals(String type, Object value) {
        return getThingType(type).equals(getThingType(value));
    }

    public Boolean equals(Object value, String type) {
        return getThingType(type).equals(getThingType(value));
    }

    public Boolean equals(Class typeClass, Object value) {
        return getThingType(typeClass).equals(getThingType(value));
    }

    public Boolean equals(Object value, Class typeClass) {
        return getThingType(typeClass).equals(getThingType(value));
    }


    public boolean convertsFromString(String thingType) {
        return getThingType(thingType).convertsFromString();
    }
}
