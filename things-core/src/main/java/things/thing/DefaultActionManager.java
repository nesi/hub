package things.thing;

import com.google.common.collect.Maps;
import rx.Observable;
import things.config.ThingActions;
import things.exceptions.ActionException;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by markus on 20/05/14.
 */
public class DefaultActionManager implements ActionManager {

    @Inject
    private ThingActions thingActions;

    @Inject
    private ThingControl thingControl;

    @Override
    public Observable<? extends Thing<?>> execute(String actionName, Observable<? extends Thing<?>> things, Map<String, String> parameters) throws ActionException {

        ThingAction ta = thingActions.get(actionName);

        if ( ta == null ) {
            throw new ActionException("Can't find action with name: " + actionName, actionName);
        }

        if ( parameters == null ) {
            parameters = Maps.newHashMap();
        }

        Observable<? extends Thing<?>> result = ta.execute(actionName, things, parameters);

        return result;
    }
}
