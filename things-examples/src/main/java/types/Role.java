package types;


import org.apache.bval.constraints.NotEmpty;
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
@Subordinate( parentClass = Person.class )
@Value( typeName = "role" )
@StringConverter( value = RoleStringConverter.class )
public class Role {

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

    public String getValue() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

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
