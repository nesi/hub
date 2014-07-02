package hub.backends.users.actions;

import com.google.common.collect.ImmutableSet;
import hub.backends.users.UserManagement;
import org.springframework.context.annotation.Bean;
import rx.Observable;
import things.thing.Thing;
import things.thing.ThingAction;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;

/**
 * Created by markus on 23/06/14.
 */
public class ReimportProjectDB implements ThingAction {

    @Inject
    private UserManagement um;

    @Override
    public Observable<? extends Thing<?>> execute(String actionName, Observable<? extends Thing<?>> things, Map<String, String> parameters) {

        um.recreate();
        return Observable.empty();
    }

    @Override
    public Set<String> getSupportedActionNames() {
        return ImmutableSet.<String>builder().add("reimport_projects").build();
    }
}
