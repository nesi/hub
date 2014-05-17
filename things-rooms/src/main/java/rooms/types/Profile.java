package rooms.types;

import com.google.common.collect.Maps;
import things.model.types.Value;
import things.model.types.attributes.UniqueKey;

import java.util.Map;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 18/04/14
 * Time: 11:16 PM
 */
@UniqueKey
@Value(typeName = "profile")
public class Profile {

    private Map<String, Light> lights = Maps.newHashMap();


    public Profile() {
    }

    public void addLight(String name, Light l2) {
        getLights().put(name, l2);
    }

    public Map<String, Light> getLights() {
        return lights;
    }

    public void setLights(Map<String, Light> lights) {
        this.lights = lights;
    }
}
