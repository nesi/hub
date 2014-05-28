package things.thing;

import com.google.common.collect.Maps;
import rx.Observable;
import things.config.ThingActions;
import things.exceptions.ActionException;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

/**
 * Default implementation of an {@link ActionManager}.
 *
 * Executes all actions in the same thread and just forwards the result of the execution.
 */
public class DefaultActionManager implements ActionManager {

    @Inject
    private ThingActions thingActions;

    @Override
    public Observable<? extends Thing<?>> execute(String actionName, Observable<? extends Thing<?>> things, Map<String, String> parameters) throws ActionException {

        Optional<ThingAction> ta = thingActions.get(actionName);

        if ( !ta.isPresent() ) {
            throw new ActionException("Can't find action with name: " + actionName, actionName);
        }

        if ( parameters == null ) {
            parameters = Maps.newHashMap();
        }

        Observable<? extends Thing<?>> result = ta.get().execute(actionName, things, parameters);

        return result;
    }
}
