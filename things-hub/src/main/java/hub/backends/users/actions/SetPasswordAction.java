package hub.backends.users.actions;

import com.google.common.collect.ImmutableSet;
import rx.Observable;
import things.thing.Thing;
import things.thing.ThingAction;

import java.util.Map;
import java.util.Set;

/**
 * Created by markus on 23/06/14.
 */
public class SetPasswordAction implements ThingAction {
    @Override
    public Observable<? extends Thing<?>> execute(String actionName, Observable<? extends Thing<?>> things, Map<String, String> parameters) {
        return Observable.empty();
    }

    @Override
    public Set<String> getSupportedActionNames() {
        return ImmutableSet.<String>builder().add("set_password").build();
    }
}
