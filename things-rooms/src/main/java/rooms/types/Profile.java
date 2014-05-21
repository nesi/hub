package rooms.types;

import com.google.common.collect.Maps;
import org.hibernate.annotations.GenericGenerator;
import things.model.types.Value;
import things.model.types.attributes.UniqueKey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
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
@Entity
public class Profile {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Transient
    private Map<String, Light> lights = Maps.newHashMap();

    public Profile() {
    }

    public void addLight(String name, Light l2) {
        getLights().put(name, l2);
    }

    public String getId() {
        return id;
    }

    public Map<String, Light> getLights() {
        return lights;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLights(Map<String, Light> lights) {
        this.lights = lights;
    }
}
