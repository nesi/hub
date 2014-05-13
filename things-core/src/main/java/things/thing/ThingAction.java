package things.thing;

import rx.Observable;

import java.util.Map;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 12/05/14
 * Time: 10:36 PM
 */
public interface ThingAction {
    public String execute(String actionName, Observable<? extends Thing<?>> things, Map<String, String> parameters);
}
