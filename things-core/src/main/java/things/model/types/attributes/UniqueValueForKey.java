package things.model.types.attributes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Determines whether all values of this type need to be unique.
 *
 * Be careful using this annotation, since performance can degrade when persisting this objects,
 * due to checks that need to be executed before storing. Using it for {@link things.model.SingleStringValue}s
 * should be ok though.
 *
 * Written by: Markus Binsteiner
 * Date: 24/01/14
 * Time: 11:17 AM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UniqueValueForKey {

    public boolean unique() default false;
}
