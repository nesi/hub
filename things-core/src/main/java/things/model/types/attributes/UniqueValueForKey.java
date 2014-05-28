package things.model.types.attributes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Determines whether all values of a type need to be unique.
 *
 * Be careful using this annotation, since performance can degrade when persisting this objects,
 * due to checks that need to be executed before storing. Using it for types that are converted to strings when stored and embedded in the Thing object should be ok though.
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface UniqueValueForKey {

    public boolean unique() default true;
}
