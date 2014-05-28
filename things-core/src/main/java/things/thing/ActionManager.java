package things.thing;

import rx.Observable;
import things.exceptions.ActionException;

import java.util.Map;

/**
 * If you need more control over how Actions are executed (e.g. everthing should be executed in a Fork/Join threadpool, or different actions need to be executed differently)
 * you can implement this interface.
 *
 * Default ActionManager is {@link things.thing.DefaultActionManager}.
 */
public interface ActionManager {
    Observable<? extends Thing<?>> execute(String actionName, Observable<? extends Thing<?>> things, Map<String, String> parameters) throws ActionException;
}
