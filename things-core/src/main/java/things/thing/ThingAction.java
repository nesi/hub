package things.thing;

import com.google.common.collect.Sets;
import rx.Observable;

import java.util.Map;
import java.util.Set;

/**
 * A ThingAction is some sort of computation, which can take a long time or not, running in the background or not.
 *
 * The computation is executed (or started) in the {@link #execute(String, rx.Observable, java.util.Map)} method, and would
 * ideally return the result of the computation as an Observable. If the computation is expected to take too long for the client
 * to wait for it, the result can also be a pointer to the result once it's finished, or some other form of status.
 *
 * If the computation is a transformation of some sort, which I'd expect to be the main use-case for this, then I would
 * advise to return the new state of the things that got transformed as result.
 */
public interface ThingAction {

    /**
     * The method that performs or starts the computation for this ThingAction.
     *
     * @param actionName the name the action is configured (in {@link things.config.ThingActions})
     * @param things     the things the computation should be performed on, or that are used as input parameters
     * @param parameters additional parameters, usually to fine-tune behaviour
     * @return result(s), link(s) to result(s), status, ...
     */
    public Observable<? extends Thing<?>> execute(String actionName, Observable<? extends Thing<?>> things, Map<String, String> parameters);

    /**
     * A list of Strings that show which action commands this Class supports.
     */
    public Set<String> getSupportedActionNames();

    /**
     * Ensures that the Things that are fed into the Action all have populated Values.
     *
     * Some actions might not need that, which will possibly increase performance because the values don't have to be loaded at all.
     *
     * @return whether this action needs loaded values for input things (true) or not (false)
     */
    default boolean ensurePopulatedValues() {
        return true;
    }

    /**
     * (not used at the moment)
     *
     * A filter that can be chained in before the action is executed, which can filter out Types that don't
     * match the ones specified here.
     */
    default Set<Class> filterTypes() {
        return Sets.newHashSet();
    }

    /**
     * (not used at the moment)
     *
     * If this method returns true, the {@link things.thing.ActionManager} is advised to run this action
     * in the background and not wait for it to finish.
     *
     * I haven't figured out which result should be returned in this case.
     *
     */
    default boolean runsInBackground() {
        return false;
    }

}
