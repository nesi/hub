package hub.types.persistent;

import things.types.SingleStringConverter;

/**
 * Created by markus on 19/05/14.
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
