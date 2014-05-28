package things.model.types.attributes;

import things.types.SingleStringConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies the {@link things.types.SingleStringConverter} that is used to convert this type to a single string.
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface StringConverter {
    public Class<? extends SingleStringConverter<?>> value();
}
