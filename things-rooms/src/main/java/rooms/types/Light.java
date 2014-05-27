package rooms.types;


import org.hibernate.validator.constraints.NotEmpty;
import things.model.types.Value;
import things.model.types.attributes.UniqueKey;

import javax.validation.constraints.NotNull;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 13/04/14
 * Time: 12:09 AM
 */
@Value(typeName = "light")
@UniqueKey
public class Light {

    @NotNull
    private Group lightGroup;
    @NotEmpty
    private String lightType;


    public Light(Group lightGroup, String type) {
        this.lightGroup = lightGroup;
        this.lightType = type;
    }

    public Light() {
    }

    public Group getLightGroup() {
        return lightGroup;
    }

    public String getLightType() {
        return lightType;
    }

    public void setLightGroup(Group lightGroup) {
        this.lightGroup = lightGroup;
    }

    public void setLightType(String type) {
        this.lightType = type;
    }

    @Override
    public String toString() {
        return "Light{" +
                "lightGroup=" + lightGroup +
                ", type='" + lightType +
                '}';
    }
}
