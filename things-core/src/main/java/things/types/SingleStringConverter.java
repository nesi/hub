package things.types;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 14/05/14
 * Time: 9:20 PM
 */
public interface SingleStringConverter<V> {

    public V convertFromString(String valueString);

    public String convertToString(V value);
}
