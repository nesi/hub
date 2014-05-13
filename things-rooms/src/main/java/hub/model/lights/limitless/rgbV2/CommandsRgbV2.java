package hub.model.lights.limitless.rgbV2;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import hub.model.lights.Cmd;
import hub.model.lights.limitless.LimitlessControllerCommand;

import java.util.List;
import java.util.Map;

/**
 * All possible commands for whiteV2 LimitlessLED lamps.
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 29/05/13
 * Time: 10:29 PM
 */
public enum CommandsRgbV2 implements LimitlessControllerCommand {
    ALL_ON(Cmd.ON, 34),
    ALL_OFF(Cmd.OFF, 33),
    BRIGHTNESS_UP(Cmd.BRIGHTNESS_UP, 35),
    BRIGHTNESS_DOWN(Cmd.BRIGHTNESS_DOWN, 36),
    DISCO_NEXT(Cmd.IGNORE, 39),
    DISCO_LAST(Cmd.IGNORE, 40),
    DISCO_SLOWER(Cmd.IGNORE, 38),
    DISCO_FASTER(Cmd.IGNORE, 37),
    SET_COLOUR(Cmd.SET_COLOUR, 32);

// ------------------------------ FIELDS ------------------------------

    private static final byte START = (byte) 32;
    private static final byte NO_CONF = (byte) 0;
    private static final byte END = (byte) 85;

    public final byte byte_to_send;
    public final Cmd cmd;

// -------------------------- STATIC METHODS --------------------------

    public static CommandsRgbV2 lookup(Cmd cmd) {
        for (CommandsRgbV2 commandsRGB : CommandsRgbV2.values()) {
            if (commandsRGB.cmd == cmd) {
                return commandsRGB;
            }
        }
        throw new RuntimeException("Can't find group/cmd combination.");
    }

    public List<byte[]> getCommand(Map<String, String> options) {

        byte[] sendData = null;
        String colour = null;
        if (options != null) {
            colour = options.get("colour");
        }

        if (Strings.isNullOrEmpty(colour)) {
            sendData = new byte[]{byte_to_send, NO_CONF, END};
        } else {
            byte colourCode = (byte) Integer.parseInt(colour);
            sendData = new byte[]{byte_to_send, colourCode, END};
        }

        List<byte[]> result = Lists.newArrayList();
        result.add(sendData);
        return result;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private CommandsRgbV2(Cmd cmd, int byte_to_send) {
        this.cmd = cmd;
        this.byte_to_send = (byte) byte_to_send;
    }
}

