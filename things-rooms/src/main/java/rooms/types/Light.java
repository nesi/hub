package rooms.types;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import things.model.types.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 13/04/14
 * Time: 12:09 AM
 */
@Value(typeName = "light")
@Entity
public class Light {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Group group;
    @NotEmpty
    private String type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Light(Group group, String type) {
        this.group = group;
        this.type = type;
    }

    public Light() {
    }

    public Group getGroup() {
        return group;
    }

    public String getType() {
        return type;
    }

    public void setGroup(Group group) {
        this.group = group;
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
