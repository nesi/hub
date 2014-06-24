package hub.backends.users.queries;

import com.google.common.collect.ImmutableSet;
import hub.backends.users.PersonReader;
import hub.backends.users.UserManagement;
import hub.backends.users.types.Person;
import hub.backends.users.types.PersonProperty;
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
public class PersonPropertyQuery implements ThingQuery {


    @Inject
    private TypeRegistry tr;
    @Inject
    private UserManagement um;

    @Override
    public Observable<? extends Thing<?>> execute(String queryName, Observable<? extends Thing<?>> things, Map<String, String> parameters) {

        Iterable<PersonProperty> propsiter = things
                .filter(p -> tr.equals(PersonProperty.class, p.getThingType()))
                .map(p -> (PersonProperty)(p.getValue())).toBlockingObservable().toIterable();

        Set<PersonProperty> props = ImmutableSet.<PersonProperty>builder().addAll(propsiter).build();

        return Observable.from(um.getAllPersons().values())
                .filter(p -> hasProperties(p, props))
                .map(p -> PersonReader.wrapPerson(tr, p));
    }

    private boolean hasProperties(Person person, Set<PersonProperty> props) {
        return props.stream().allMatch(prop -> person.matchesProperty(prop));
    }

    @Override
    public Set<String> getSupportedQueryNames() {
        return ImmutableSet.<String>builder().add("person").build();
    }
}
