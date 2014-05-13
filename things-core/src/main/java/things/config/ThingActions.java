package things.config;

import com.google.common.collect.Maps;
import things.thing.ThingAction;

import java.util.Map;

/**
 * @author: Markus Binsteiner
 */
public class ThingActions {

    private final Map<String, ThingAction> thingActions = Maps.newHashMap();

    public ThingActions() {
    }

    public void addAction(String matcher, ThingAction action) {

        thingActions.put(matcher, action);

    }
    
    public ThingAction get(String actionName) {

        return thingActions.get(actionName);
    }

}
