package hub.backends.users.types;

import hub.backends.users.converters.UsernameStringConverter;
import things.model.types.Value;
import things.model.types.attributes.StringConverter;

import java.util.Objects;

/**
 * Created by markus on 23/06/14.
 */
@Value(typeName = "username")
@StringConverter(value = UsernameStringConverter.class)
public class Username {

    private String username;

    public Username(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == this ) return true;
        if ( obj == null ) return false;

        if ( getClass().equals(obj.getClass()) ) {
            final Username other = (Username) obj;
            return Objects.equals(getUsername(), other.getUsername());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUsername());
    }
}
