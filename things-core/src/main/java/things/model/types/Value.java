package things.model.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker to tag a class as being usable as a {@link things.types.ThingType} when using the {@link things.types.AnnotationTypeFactory} way of gathering types.
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface Value {

    public String typeName();
}
