package things.types;

/**
 * A SingleStringConverter can convert a type ({@link things.types.ThingType}) into a String, and back.
 */
public interface SingleStringConverter<V> {

    /**
     * Convert the String into a value.
     */
    public V convertFromString(String valueString);

    /**
     * Convert an instance of a value into a String.
     */
    public String convertToString(V value);
}
