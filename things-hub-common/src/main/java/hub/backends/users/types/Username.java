package hub.backends.users.types;

import things.model.types.Value;

import java.util.Objects;

/**
 * Created by markus on 23/06/14.
 */
@Value(typeName = "username")
public class Username {

    private String service;
    private String username;

    public Username(String service, String username) {
        this.service = service;
        this.username = username;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
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
            return Objects.equals(getUsername(), other.getUsername())
                    && Objects.equals(getService(), other.getService());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getService(), getUsername());
    }
}
