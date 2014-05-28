package things.config;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import things.thing.ThingAction;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Collection class that holds all {@link things.thing.ThingAction}s for this instance.
 *
 * Every action needs to be mapped to an action name, so the framework knows how to
 * execute it. One {@link things.thing.ThingAction}-class can be mapped multiple times
 * to different commands, the implementing class itself can then check which name it was called under.
 *
 * Execution of actions happens in {@link things.thing.ThingControlMinimal#executeAction(String, rx.Observable, java.util.Map)}.
 */
public class ThingActions {

    private final Map<String, ThingAction> thingActions = Maps.newHashMap();

    public ThingActions() {
    }

    /**
     * Add a new action.
     */
    public void addAction(ThingAction action) {

        Set<String> names = action.getSupportedActionNames();
        names.forEach(n -> thingActions.put(n, action));

    }

    /**
     * Gets the action with the specified name, otherwise an empty Optional.
     *
     * @param actionName the action name
     * @return the action
     */
    public Optional<ThingAction> get(String actionName) {

        Preconditions.checkArgument(!Strings.isNullOrEmpty(actionName), "Action name can't be empty");

        return Optional.ofNullable(thingActions.get(actionName));
    }

}
