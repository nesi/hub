package things.model.types.attributes;

import things.types.SingleStringConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by markus on 19/05/14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface StringConverter {
    public Class<? extends SingleStringConverter<?>> value();
}
