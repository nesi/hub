package hub.backends.users;

import hub.backends.users.types.Person;
import rx.Observable;
import things.thing.AbstractThingReader;
import things.thing.Thing;
import things.thing.ThingControl;
import things.types.TypeRegistry;
import things.utils.MatcherUtils;

import javax.inject.Inject;

/**
 * Created by markus on 19/06/14.
 */
public class PersonReader extends AbstractThingReader {

    @Inject
    private TypeRegistry tr;
    @Inject
    private ThingControl tc;
    @Inject
    private UserManagement um;


    @Override
    public Observable<? extends Thing<?>> findAllThings() {
        return Observable.from(um.getAllPersons().values()).map(p -> wrapPerson(p));
    }

    @Override
    public Observable<? extends Thing<?>> findThingForId(String id) {

            Person p = um.getAllPersons().get(id);
            if ( p == null ) {
                return Observable.empty();
            }
            Thing<Person> personThing = wrapPerson(p);

            return Observable.just(personThing);

    }

    @Override
    public Observable<? extends Thing<?>> findThingsMatchingTypeAndKey(String type, String key) {

        return Observable.from(um.getAllPersons().keySet())
                .filter(n -> MatcherUtils.wildCardMatch(n, key))
                .map(n -> um.getAllPersons().get(n))
                .map(p -> wrapPerson(p));
    }

    @Override
    public Observable<? extends Thing<?>> getChildrenForId(String id) {
        return Observable.empty();
    }

    @Override
    public Observable<? extends Thing<?>> getChildrenMatchingTypeAndKey(Observable<? extends Thing<?>> things, String typeMatcher, String keyMatcher) {
        return Observable.empty();
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForTypeAndKey(String type, String key) {
        return Observable.just(um.getAllPersons().get(key)).filter(alias -> alias != null ).map(p -> wrapPerson(p));
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForTypeMatchingKey(String type, String key) {
        return findThingsMatchingTypeAndKey(type, key);
    }

    public static Thing<Person> wrapPerson(TypeRegistry tr, Person p) {

        Thing t = new Thing();
        String identifier = p.getAlias();
        t.setId(identifier);

        t.setThingType(tr.getType(Person.class));
        t.setValue(p);
        t.setKey(identifier);
        t.setValueIsPopulated(true);

        return t;
    }

    public Thing<Person> wrapPerson(Person p) {
        return wrapPerson(tr, p);
    }

    @Override
    public <V> V readValue(Thing<V> thing) {

        // person will always be populated
        return thing.getValue();
    }
}
