package things.model.types;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 6/05/14
 * Time: 10:13 PM
 */
public interface SimpleValue<T> {

    abstract T getValue();

    abstract void setValue(T value);
}
