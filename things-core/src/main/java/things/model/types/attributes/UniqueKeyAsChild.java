package things.model.types.attributes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tags a type as one where when a thing has got multiple childs of this type, all of those must have different keys.
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface UniqueKeyAsChild {

    public boolean unique() default true;
}
