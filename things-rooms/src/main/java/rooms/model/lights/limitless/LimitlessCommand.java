package rooms.model.lights.limitless;

import com.google.common.collect.Maps;
import rooms.types.Group;

import java.util.Map;

/**
 * Project: rooms
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 6/02/14
 * Time: 9:41 PM
 */
public class LimitlessCommand {
// ------------------------------ FIELDS ------------------------------

    private LimitlessControllerCommand command;
    private Group group;

    private Map<String, String> options = Maps.newConcurrentMap();

// --------------------------- CONSTRUCTORS ---------------------------

    public LimitlessCommand(LimitlessControllerCommand command, Group group, Map<String, String> options) {
        this.command = command;
        this.group = group;
        this.options = options;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public LimitlessControllerCommand getCommand() {
        return command;
    }

    public void setCommand(LimitlessControllerCommand command) {
        this.command = command;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

// -------------------------- OTHER METHODS --------------------------

    public void addOption(String key, String value) {
        getOptions().put(key, value);
    }

    public void removeOption(String key) {
        getOptions().remove(key);
    }

    @Override
    public String toString() {
        return "LimitlessCommand{" +
                "command=" + command +
                ", group=" + group +
                ", options=" + options +
                '}';
    }
}
