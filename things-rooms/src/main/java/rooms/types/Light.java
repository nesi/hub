package rooms.types;


import org.hibernate.validator.constraints.NotEmpty;
import things.model.types.Value;

import javax.validation.constraints.NotNull;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 13/04/14
 * Time: 12:09 AM
 */
@Value(typeName = "light")
public class Light {

    @NotNull
    private Group group;
    @NotEmpty
    private String type;

    public Light(Group group, String type) {
        this.group = group;
        this.type = type;
    }

    public Light() {
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Light{" +
                "group=" + group +
                ", type='" + type +
                '}';
    }
}
