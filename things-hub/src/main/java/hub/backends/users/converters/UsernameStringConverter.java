package hub.backends.users.converters;

import hub.backends.users.types.Username;
import things.types.SingleStringConverter;

/**
 * Created by markus on 23/06/14.
 */
public class UsernameStringConverter implements SingleStringConverter<Username> {
    @Override
    public Username convertFromString(String valueString) {
        return new Username(valueString);
    }

    @Override
    public String convertToString(Username value) {
        return value.getUsername();
    }
}
