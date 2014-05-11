package rooms.model.lights.limitless.whiteV2;

import com.google.common.collect.Lists;
import rooms.model.lights.Cmd;
import rooms.model.lights.limitless.LimitlessControllerCommand;
import rooms.types.Group;

import java.util.List;
import java.util.Map;

/**
 * All possible commands for whiteV2 LimitlessLED lamps.
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 29/05/13
 * Time: 10:29 PM
 */
public enum CommandsWhiteV2 implements LimitlessControllerCommand {

    ALL_ON(Cmd.ON, Group.GROUP_ALL, 53),
    ALL_OFF(Cmd.OFF, Group.GROUP_ALL, 57),
    BRIGHTNESS_UP(Cmd.BRIGHTNESS_UP, null, 60),
    BRIGHTNESS_DOWN(Cmd.BRIGHTNESS_DOWN, null, 52),
    WARM_UP(Cmd.WARMTH_UP, null, 62),
    WARM_DOWN(Cmd.WARMTH_DOWN, null, 63),
    GROUP_1_ON(Cmd.ON, Group.GROUP_1, 56),
    GROUP_1_OFF(Cmd.OFF, Group.GROUP_1, 59),
    GROUP_2_ON(Cmd.ON, Group.GROUP_2, 61),
    GROUP_2_OFF(Cmd.OFF, Group.GROUP_2, 51),
    GROUP_3_ON(Cmd.ON, Group.GROUP_3, 55),
    GROUP_3_OFF(Cmd.OFF, Group.GROUP_3, 58),
    GROUP_4_ON(Cmd.ON, Group.GROUP_4, 50),
    GROUP_4_OFF(Cmd.OFF, Group.GROUP_4, 54),
    NIGHT_MODE_ALL(ALL_OFF, Cmd.NIGHTMODE, Group.GROUP_ALL, 185),
    NIGHT_MODE_GROUP_1(GROUP_1_OFF, Cmd.NIGHTMODE, Group.GROUP_1, 187),
    NIGHT_MODE_GROUP_2(GROUP_2_OFF, Cmd.NIGHTMODE, Group.GROUP_2, 179),
    NIGHT_MODE_GROUP_3(GROUP_3_OFF, Cmd.NIGHTMODE, Group.GROUP_3, 186),
    NIGHT_MODE_GROUP_4(GROUP_4_OFF, Cmd.NIGHTMODE, Group.GROUP_4, 182),
    FULL_ALL(ALL_ON, Cmd.FULL, Group.GROUP_ALL, 181),
    FULL_GROUP_1(GROUP_1_ON, Cmd.FULL, Group.GROUP_1, 184),
    FULL_GROUP_2(GROUP_2_ON, Cmd.FULL, Group.GROUP_2, 189),
    FULL_GROUP_3(GROUP_3_ON, Cmd.FULL, Group.GROUP_3, 183),
    FULL_GROUP_4(GROUP_4_ON, Cmd.FULL, Group.GROUP_4, 178);

// ------------------------------ FIELDS ------------------------------

    private static final byte NO_CONF = (byte) 0;
    private static final byte END = (byte) 85;

    public final byte byte_to_send;
    public final Cmd cmd;
    public final Group group;
    public final CommandsWhiteV2 before;

// -------------------------- STATIC METHODS --------------------------


    public static CommandsWhiteV2 lookup(Group group, Cmd cmd) {
        for ( CommandsWhiteV2 commandsWhite : CommandsWhiteV2.values() ) {
            if ( commandsWhite.group == group && commandsWhite.cmd == cmd) {
                return commandsWhite;
            }
        }

        for ( CommandsWhiteV2 commandsWhite : CommandsWhiteV2.values() ) {
            if ( commandsWhite.cmd == cmd) {
                return commandsWhite;
            }
        }
        throw new RuntimeException("Can't find group/cmd combination.");
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private CommandsWhiteV2(Cmd cmd, Group group, int byte_to_send) {
        this(null, cmd, group, byte_to_send);
    }

    private CommandsWhiteV2(CommandsWhiteV2 before, Cmd cmd, Group group, int byte_to_send) {
        this.before = before;
        this.cmd = cmd;
        this.byte_to_send = (byte) byte_to_send;
        this.group = group;
    }

// -------------------------- OTHER METHODS --------------------------

    private List<byte[]> getCommandSequence() {

        List<byte[]> result = Lists.newArrayList();

        if (this.before != null) {
            List<byte[]> beforeResult = this.before.getCommandSequence();
            result.addAll(beforeResult);
        }
        byte[] sendData = new byte[]{byte_to_send, NO_CONF, END};
        result.add(sendData);

        return result;
    }

    public List<byte[]> getCommand(Map<String, String> options) {
        return getCommandSequence();
    }
}

