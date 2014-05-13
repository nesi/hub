package types;


import org.apache.bval.constraints.NotEmpty;
import things.model.types.SimpleValue;
import things.model.types.Value;
import things.model.types.attributes.Subordinate;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 12/04/14
 * Time: 7:39 PM
 */
@Subordinate(parentClass = Person.class)
@Value(typeName = "role")
public class Role implements SimpleValue<String> {

    @NotEmpty
    private String role;

    public Role(String role) {
        setRole(role);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getValue() {
        return role;
    }

    @Override
    public void setValue(String value) {
        setRole(role);
    }

    @Override
    public String toString() {
        return "Role{" +
                "role='" + role + '\'' +
                '}';
    }
}
