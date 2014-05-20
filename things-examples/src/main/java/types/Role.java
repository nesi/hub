package types;


import org.apache.bval.constraints.NotEmpty;
import things.model.types.SimpleValue;
import things.model.types.Value;
import things.model.types.attributes.StringConverter;
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
@StringConverter(value = RoleStringConverter.class)
public class Role implements SimpleValue<String> {

    public String id;

    @NotEmpty
    private String role;

    private Role() {

    }

    public Role(String role) {
        setRole(role);
    }

    public String getRole() {
        return role;
    }

    @Override
    public String getValue() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
