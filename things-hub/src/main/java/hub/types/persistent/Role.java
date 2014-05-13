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
public class Role  {

    @NotEmpty
    private String rolename;

    public Role(String rolename) {
        this.rolename = rolename;
    }

    public Role() {
    }

    public String getValue() {
        return rolename;
    }

    public void setValue(String value) {
        this.rolename = value;
    }

    public int hashCode() {
        return Objects.hashCode(getValue());
    }

    @Override
    public String toString() {
        return getValue();
    }
}
