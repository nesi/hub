package hub.readers;

import hub.actions.UserManagement;
import hub.types.dynamic.Person;
import rx.Observable;
import things.exceptions.ThingRuntimeException;
import things.thing.AbstractThingReader;
import things.thing.Thing;
import things.thing.ThingControl;
import things.types.TypeRegistry;
import things.utils.MatcherUtils;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

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

        if ( ! id.startsWith("adviserId:") && ! id.startsWith("researcherId:") ) {
            return Observable.empty();
        }


        String type = "n/a";
        try {
            type = id.split(":")[0];
            String id_string = id.split(":")[1];
            Integer id_int = Integer.parseInt(id_string);

            String personName = null;

            switch(type ){
                case "adviserId":
                    personName = um.getResearcher(id_int);
                    break;
                case "researcherId":
                    personName = um.getAdviser(id_int);
                    break;
                default:
                    return Observable.empty();
            }
            Person p = um.getAllPersons().get(personName);
            if ( p == null ) {
                return Observable.empty();
            }
            Thing<Person> personThing = wrapPerson(p);

            return Observable.just(personThing);

        } catch (Exception e) {
            throw new ThingRuntimeException("Can't parse "+type+" id "+id+": "+e.getLocalizedMessage());
        }
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
        return Observable.just(um.getAllPersons().get(key)).map(p -> wrapPerson(p));
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForTypeMatchingKey(String type, String key) {
        return findThingsMatchingTypeAndKey(type, key);
    }

    public Thing<Person> wrapPerson(Person p) {

        Thing t = new Thing();
        String identifier = um.createIdentifier(p);
        Optional<Integer> adviserId = um.getAdviserId(identifier);
        Optional<Integer> researcherId = um.getResearcherId(identifier);

        String id = null;
        if ( adviserId.isPresent() ) {
            id = "adviserId:"+adviserId.get();
        } else if ( researcherId.isPresent() ) {
            id = "researcherId:"+researcherId.get();
        }

        t.setId(id);
        t.setThingType(tr.getType(Person.class));
        t.setValue(p);
        t.setKey(identifier);
        t.setValueIsPopulated(true);

        return t;
    }

    @Override
    public <V> V readValue(Thing<V> thing) {

        // person will always be populated
        return thing.getValue();
    }
}
