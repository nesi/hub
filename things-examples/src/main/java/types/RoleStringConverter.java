package types;

import things.types.SingleStringConverter;

/**
 * Created by markus on 20/05/14.
 */
public class RoleStringConverter implements SingleStringConverter<Role> {
    @Override
    public Role convertFromString(String valueString) {
        return new Role(valueString);
    }

    @Override
    public String convertToString(Role value) {
        return value.getRole();
    }
}
