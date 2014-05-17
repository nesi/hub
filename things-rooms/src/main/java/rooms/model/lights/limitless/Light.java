package rooms.model.lights.limitless;

import java.util.Map;

/**
 * Project: rooms
 * <p>
 * Written by: Markus Binsteiner
 * Date: 6/02/14
 * Time: 11:40 PM
 */
public interface Light {

    void decreaseBrightness(int steps);

    void decreaseWarmth(int steps);

    String getName();

    void increaseBrightness(int steps);

    void increaseWarmth(int steps);

    Boolean isOn();

    void set(Map<String, String> properties);

    void setBrightness(int absBrightness);

    void setColor(int color);

    void setOn(Boolean on);

    void setWarmth(int absWarmth);


}
