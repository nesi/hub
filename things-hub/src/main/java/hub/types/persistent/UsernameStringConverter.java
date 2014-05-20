package hub.types.persistent;

import things.types.SingleStringConverter;


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
