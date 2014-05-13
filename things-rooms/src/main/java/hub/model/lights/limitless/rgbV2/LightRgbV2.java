package hub.model.lights.limitless.rgbV2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hub.model.lights.Cmd;
import hub.model.lights.limitless.Light;
import hub.model.lights.limitless.LimitlessLEDControllerV2;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static hub.model.lights.Cmd.BRIGHTNESS_DOWN;

/**
 * Project: hub
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 6/02/14
 * Time: 12:04 PM
 */
public class LightRgbV2 implements Light {
// ------------------------------ FIELDS ------------------------------

    public static final int VIOLET = 0;
    public static final int  ROYAL_BLUE = 16;
    public static final int  BABY_BLUE = 32;
    public static final int  AQUA = 48;
    public static final int  MINT = 64;
    public static final int  SEAFOAM_GREEN = 80;
    public static final int  GREEN = 96;
    public static final int  LIME_GREEN = 112;
    public static final int  YELLOW = 128;
    public static final int  YELLOW_ORANGE = 144;
    public static final int  ORANGE = 160;
    public static final int  RED = 176;
    public static final int  PINK = 192;
    public static final int  FUSIA = 208;
    public static final int  LILAC = 224;
    public static final int  LAVENDAR = 240;


    private static final Logger myLogger = LoggerFactory.getLogger(LightRgbV2.class);

    public static final int CALIBRATION_THRESHOLD = 11;
    private static final Integer BRIGHTNESS_MAX = 10;
    private static final Integer BRIGHTNESS_MIN = 0;

    private final LimitlessLEDControllerV2 controller;

    private Boolean on = null;
    private Integer brightness = null;
    private Integer brightness_shadow = 0;

    private Integer colour = null;

    private String name;

    private boolean default_calibrate_brightness_full = false;


// --------------------------- CONSTRUCTORS ---------------------------

    public LightRgbV2(String name, LimitlessLEDControllerV2 controller) {
        this.name = name;
        this.controller = controller;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public Integer getBrightness() {
        return brightness;
    }

// ------------------------ CANONICAL METHODS ------------------------

    public String toString() {
        String brightness_string = brightness.toString();
        if (brightness == null) {
            brightness_string = "n/a";
        }
        String colour_string = colour.toString();
        if ( colour == null ) {
            colour_string = "n/a";
        }
        String result = name + " - (state: " + on + ", brightness: " + brightness_string + ", colour: "+colour_string+")";
        return result;
    }

// -------------------------- OTHER METHODS --------------------------

    public void calibrate() {
        Integer old_brightness = brightness;
        Integer old_colour = colour;
        setOn(true);

        calibrateBrightness(default_calibrate_brightness_full);

        if ( old_brightness == null ) {
            if ( default_calibrate_brightness_full ) {
                old_brightness = BRIGHTNESS_MAX;
            } else {
                old_brightness = BRIGHTNESS_MIN;
            }
        }

        setBrightness(old_brightness);
    }

    private void send(Cmd command) {
        controller.sendRGB(command);
    }

    public void calibrateBrightness(boolean full) {
        setOn(true);
        if (full) {
            send(Cmd.BRIGHTNESS_UP, CALIBRATION_THRESHOLD);
            this.brightness = BRIGHTNESS_MAX;
        } else {
            send(BRIGHTNESS_DOWN, CALIBRATION_THRESHOLD);
            this.brightness = BRIGHTNESS_MIN;
        }
        brightness_shadow = 0;
    }

    public void setOn(Boolean on) {
        if (this.on == null) {
            if (on) {
                send(Cmd.ON);
                this.on = true;
            } else {
                send(Cmd.OFF);
                this.on = false;
            }
        } else {
            if (!this.on.equals(on)) {
                if (on) {
                    send(Cmd.ON);
                    this.on = true;
                } else {
                    send(Cmd.OFF);
                    this.on = false;
                }
            }
        }
    }

    public void toggle() {
        if ( isOn() == null || ! isOn() ) {
            setOn(true);
        } else {
            setOn(false);
        }
    }

    @Override
    public Boolean isOn() {
        return this.on;
    }

    public void send(Cmd command, int times) {
        controller.sendRGB(command, times);
    }

    public void increaseBrightness() {
        increaseBrightness(1);
    }

    public void decreaseBrightness() {
        decreaseBrightness(1);
    }

    public void decreaseBrightness(int steps) {
        changeBrightness(-steps);
    }

    public void changeBrightness(int steps) {
        if (steps == 0) {
            return;
        }

        if (brightness != null && (
                (steps < 0 && brightness == BRIGHTNESS_MIN) || (steps > 0 && brightness == BRIGHTNESS_MAX))
                ) {
            return;
        }

        if (steps > 0) {
            controller.sendRGB(Cmd.BRIGHTNESS_UP, steps);
        } else {
            controller.sendRGB(BRIGHTNESS_DOWN, Math.abs(steps));
        }
        if (brightness != null) {
            brightness = brightness + steps;
        } else {
            brightness_shadow = brightness_shadow + steps;
            if (Math.abs(brightness_shadow) >= CALIBRATION_THRESHOLD) {
                if (brightness_shadow > 0) {
                    brightness = BRIGHTNESS_MAX;
                } else {
                    brightness = BRIGHTNESS_MIN;
                }
                brightness_shadow = 0;
            }
        }
        myLogger.debug("Changed brightness ({} steps), new state: {}", steps, debugState());
    }

    public String debugState() {
        String onString = null;
        if (on != null) {
            if (on) {
                onString = "on";
            } else {
                onString = "off";
            }
        }
        return "state: " + onString + ", brightness " + brightness + " (shadow: " + brightness_shadow + "), colour "+colour;
    }

    public void increaseBrightness(int steps) {
        changeBrightness(steps);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setBrightness(int absBrightness) {
        setOn(true);
        if (this.brightness == null) {
            calibrateBrightness(absBrightness > BRIGHTNESS_MAX/2);
        }

        int delta_brightness = absBrightness - this.brightness;

        myLogger.debug("Changing brightness (" + delta_brightness + " steps)");

        changeBrightness(delta_brightness);
    }

    public void setColor(int colour) {
        controller.sendRGB(Cmd.SET_COLOUR, colour);
        this.colour = colour;
    }

    @Override
    public void setWarmth(int absWarmth) {
        throw new NotImplementedException();
    }

    @Override
    public void increaseWarmth(int steps) {
        throw new NotImplementedException();
    }

    @Override
    public void decreaseWarmth(int steps) {
        throw new NotImplementedException();
    }
}
