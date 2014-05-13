package hub.model.lights.limitless;

/**
 * Project: hub
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 6/02/14
 * Time: 11:40 PM
 */
public interface Light {

    String getName();

     void setBrightness(int absBrightness);

     void increaseBrightness(int steps);

     void decreaseBrightness(int steps);

     void setColor(int color);

     void setWarmth(int absWarmth);

     void increaseWarmth(int steps);

     void decreaseWarmth(int steps);

     void setOn(Boolean on);

    Boolean isOn();




}
