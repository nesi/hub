package rooms.types;


import org.hibernate.validator.constraints.Range;
import things.model.types.Value;

import java.io.Serializable;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 2/05/14
 * Time: 7:34 PM
 */
@Value(typeName = "lightState")
public class LightState implements Serializable {

    @Range(min = 0, max = 10)
    private Integer brightness = null;
    @Range(min = 0, max = 255)
    private Integer color = null;
    @Range(min = 0, max = 10)
    private Integer warmth = null;
    private Boolean on = null;

    public Boolean isOn() {
        return on;
    }

    public void setOn(Boolean on) {
        this.on = on;
    }

    public Integer getWarmth() {
        return warmth;
    }

    public void setWarmth(Integer warmth) {
        this.warmth = warmth;
    }

    public Integer getBrightness() {
        return brightness;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "LightState{" +
                "brightness=" + brightness +
                ", color=" + color +
                ", warmth=" + warmth +
                ", on=" + on +
                '}';
    }
}
