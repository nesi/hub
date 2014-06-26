package hub.backends.users.queries;

import com.google.common.collect.ImmutableSet;
import hub.backends.users.PersonReader;
import hub.backends.users.UserManagement;
import hub.backends.users.types.Person;
import hub.backends.users.types.Username;
import rx.Observable;
import things.thing.Thing;
import things.thing.ThingQuery;
import things.types.TypeRegistry;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;

/**
 * Created by markus on 24/06/14.
 */
public class UsernameQuery implements ThingQuery {


    @Inject
    private TypeRegistry tr;
    @Inject
    private UserManagement um;

    @Override
    public Observable<? extends Thing<?>> execute(String queryName, Observable<? extends Thing<?>> things, Map<String, String> parameters) {

        Username username = things
                .filter(p -> tr.equals(Username.class, p.getThingType()))
                .map(p -> (Username)(p.getValue())).toBlockingObservable().single();

        return findPersonForUsername(username);
    }


    public Observable<Thing<Person>> findPersonForUsername(Username un) {
        return Observable.from(um.getAllPersons().values())
                .filter(p -> p.hasUsername(un))
                .single()
                .map(p -> PersonReader.wrapPerson(tr, p));

    }

    @Override
    public Set<String> getSupportedQueryNames() {
        return ImmutableSet.<String>builder().add("username").build();
    }
}
