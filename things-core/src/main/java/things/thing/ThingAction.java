package things.thing;

import com.google.common.collect.Sets;
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
public interface ThingAction {

    default boolean runsInBackground() {
        return false;
    }

    default Set<Class> filterTypes() {
        return Sets.newHashSet();
    }

    default boolean ensurePopulatedValues() {
        return true;
    }

    public Observable<? extends Thing<?>> execute(String actionName, Observable<? extends Thing<?>> things, Map<String, String> parameters);
}
