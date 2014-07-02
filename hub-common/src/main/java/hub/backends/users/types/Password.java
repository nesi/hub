package hub.backends.users.types;

import org.hibernate.validator.constraints.NotBlank;
import things.model.types.Value;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by markus on 25/06/14.
 */
@Entity(name = "password")
@IdClass(PasswordId.class)
@Value(typeName = "password")
public class Password implements Serializable {

    @Id
    @NotBlank
    private String service;
    @Id
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public Password() {
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == this ) return true;
        if ( obj == null ) return false;

        if ( getClass().equals(obj.getClass()) ) {
            final Password other = (Password) obj;
            return Objects.equals(getUsername(), other.getUsername())
                    && Objects.equals(getService(), other.getService());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getService());
    }

    public String getUsername() {
        return username;
    }

    public void setPerson(String username) {
        this.username = username;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
