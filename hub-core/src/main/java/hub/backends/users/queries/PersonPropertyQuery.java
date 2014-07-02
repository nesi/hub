package hub.backends.users.queries;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import hub.backends.users.PersonReader;
import hub.backends.users.UserManagement;

import hub.backends.users.types.Person;
import hub.backends.users.types.Property;
import rx.Observable;
import things.thing.Thing;
import things.thing.ThingQuery;
import things.types.TypeRegistry;


import javax.inject.Inject;
import java.util.Collection;
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

        Iterable<Property> propsiter = things
                .filter(p -> tr.equals(Property.class, p.getThingType()))
                .map(p -> (Property)(p.getValue())).toBlockingObservable().toIterable();

        Set<Property> props = ImmutableSet.<Property>builder().addAll(propsiter).build();

        return findPersonsForProperty(props);
    }

    public Observable<Thing<Person>> findPersonsForProperty(Property pp) {
        return findPersonsForProperty(Sets.newHashSet(pp));
    }

    public Observable<Thing<Person>> findPersonsForProperty(Collection<Property> pp) {
        return Observable.from(um.getAllPersons().values())
                .filter(p -> hasProperties(p, pp))
                .map(p -> PersonReader.wrapPerson(tr, p));
    }

    private boolean hasProperties(Person person, Collection<Property> props) {
        return props.stream().allMatch(prop -> person.matchesProperty(prop));
    }

    @Override
    public Set<String> getSupportedQueryNames() {
        return ImmutableSet.<String>builder().add("property").build();
    }
}
