package hub.backends.users.queries;

import com.google.common.collect.ImmutableSet;
import hub.auth.HubUserDetails;
import hub.backends.users.types.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import rx.Observable;
import things.thing.Thing;
import things.thing.ThingQuery;
import things.types.TypeRegistryImpl;

import java.util.Map;
import java.util.Set;

/**
 * Created by markus on 8/07/14.
 */
public class SessionQuery implements ThingQuery {

    @Autowired
    private TypeRegistryImpl tr;

    @Override
    public Observable<? extends Thing<?>> execute(String queryName, Observable<? extends Thing<?>> things, Map<String, String> parameters) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HubUserDetails ud = (HubUserDetails) auth.getPrincipal();

        Person p = ud.getPerson();

        Thing<Person> t = Thing.createThingPoJo(tr, p.getAlias(), p);

        return Observable.from(t);

    }

    @Override
    public Set<String> getSupportedQueryNames() {
        return ImmutableSet.<String>builder().add("session").build();
    }
}
