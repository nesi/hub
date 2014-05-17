package hub.types.persistent;

import org.hibernate.validator.constraints.NotEmpty;
import things.model.types.Value;
import things.model.types.attributes.Subordinate;
import things.model.types.attributes.UniqueKeyInOtherThings;
import things.model.types.attributes.UniqueValueForKey;

import java.util.Objects;

/**
 * A role is just an arbitrary String which will be used to determine
 * permissions by an authorization engine.
 */
@UniqueKeyInOtherThings(unique = false)
@Subordinate(parentClass = Person.class)
@Value(typeName = "role")
@UniqueValueForKey(unique = true)
public class Role {

    @NotEmpty
    private String role;

    public Role(String role) {
        this.role = role;
    }

    public Role() {
    }

    public String getRole() {
        return role;
    }

    public int hashCode() {
        return Objects.hashCode(getRole());
    }

    public void setRole(String value) {
        this.role = value;
    }

    @Override
    public String toString() {
        return getRole();
    }
}
