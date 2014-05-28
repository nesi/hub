package hub.queries.users;

import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingQuery;

import java.util.Map;
import java.util.Set;

/**
 * Created by markus on 28/05/14.
 */
public class GroupQuery implements ThingQuery {

    @Autowired
    private ThingControl tc;

    @Override
    public Observable<? extends Thing<?>> execute(String queryName, Observable<? extends Thing<?>> things, Map<String, String> parameters) {
        return null;
    }

    @Override
    public Set<String> getSupportedQueryNames() {
        return ImmutableSet.<String>of("users");
    }
}
