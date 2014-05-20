package hub.queries.users;

import com.google.common.collect.ImmutableSet;
import hub.actions.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;
import things.exceptions.QueryRuntimeException;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingQuery;
import things.types.TypeRegistry;

import java.util.Map;
import java.util.Set;

/**
 * Created by markus on 19/05/14.
 */
public class UserQuery implements ThingQuery {

    @Autowired
    private ThingControl tc;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private TypeRegistry typeRegistry;

    @Override
    public Observable<? extends Thing<?>> execute(String queryName, Observable<? extends Thing<?>> things, Map<String, String> parameters) {

        switch(queryName) {
            case "details":
                return userUtils.convertToPerson(things).map(t -> userUtils.createUser(t));
            default: throw new QueryRuntimeException("Can't find query with name: "+queryName);
        }

    }

    @Override
    public Set<String> getSupportedQueryNames() {
        return ImmutableSet.<String>builder().add("details").build();
    }
}
