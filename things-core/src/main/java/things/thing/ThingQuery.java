package things.thing;

import rx.Observable;

import java.util.Map;
import java.util.Set;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 12/05/14
 * Time: 10:36 PM
 */
public interface ThingQuery {

    public Observable<? extends Thing<?>> execute(String queryName, Observable<? extends Thing<?>> things, Map<String, String> parameters);

    public Set<String> getSupportedQueryNames();
}
