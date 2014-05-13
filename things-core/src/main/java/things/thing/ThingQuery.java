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
public interface ThingQuery {
    public String execute(String queryName, Observable<Thing> things, Map<String, String> parameters);
}
