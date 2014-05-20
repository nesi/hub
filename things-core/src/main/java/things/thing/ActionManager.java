package things.thing;

import rx.Observable;
import things.exceptions.ActionException;

import java.util.Map;

/**
 * Created by markus on 20/05/14.
 */
public interface ActionManager {
    Observable<? extends Thing<?>> execute(String actionName, Observable<? extends Thing<?>> things, Map<String, String> parameters) throws ActionException;
}
