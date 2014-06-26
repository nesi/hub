package hub.backends.users;

import hub.backends.users.types.Person;
import hub.backends.users.types.Property;
import rx.Observable;
import things.thing.AbstractThingReader;
import things.thing.Thing;
import things.types.TypeRegistry;
import things.utils.MatcherUtils;

import javax.inject.Inject;

/**
 * Created by markus on 19/06/14.
 */
public class PersonPropertyReader extends AbstractThingReader {

    @Inject
    private UserManagement um;
    @Inject
    private TypeRegistry tr;

    @Override
    public Observable<? extends Thing<?>> findAllThings() {
        return Observable.from(um.getAllPersons().values())
                .flatMap(p -> Observable.from(p.getProperties()))
                .map(p -> wrapPersonProperty(p));
    }

    @Override
    public Observable<? extends Thing<?>> findThingForId(String id) {
        return Observable.empty();
    }

    @Override
    public Observable<? extends Thing<?>> findThingsMatchingTypeAndKey(String type, String key) {
        return Observable.from(um.getAllPersons().values())
                .flatMap(p -> Observable.from(p.getProperties()))
                .filter(prop -> MatcherUtils.wildCardMatch(prop.getKey(), key))
                .map(prop -> wrapPersonProperty(prop));
    }

    @Override
    public Observable<? extends Thing<?>> getChildrenForId(String id) {
        return Observable.from(um.getAllPersons().get(id).getProperties())
                .map(prop -> wrapPersonProperty(prop));
    }

    @Override
    public Observable<? extends Thing<?>> getChildrenMatchingTypeAndKey(Observable<? extends Thing<?>> things, String typeMatcher, String keyMatcher) {
        return things
                .filter(t -> tr.equals(Person.class, t.getThingType()))
                .map(t -> (Person) t.getValue())
                .flatMap(p -> Observable.from(p.getProperties()))
                .filter(prop -> MatcherUtils.wildCardMatch(prop.getKey(), keyMatcher))
                .map(prop -> wrapPersonProperty(prop));
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForTypeAndKey(String type, String key) {
        return Observable.from(um.getAllPersons().values())
                .flatMap(p -> Observable.from(p.getProperties()))
                .filter(prop -> MatcherUtils.wildCardMatch(prop.getKey(), key))
                .map(prop -> wrapPersonProperty(prop));
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForTypeMatchingKey(String type, String key) {

        return Observable.from(um.getAllPersons().values())
                .flatMap(p -> Observable.from(p.getProperties()))
                .filter(prop -> prop.getKey().equals(key))
                .map(prop -> wrapPersonProperty(prop));
    }

    private Thing<Property> wrapPersonProperty(Property p) {
        Thing t = new Thing();
        t.setThingType(tr.getType(Property.class));
        t.setValue(p);
        t.setKey(p.getKey());
        t.setValueIsPopulated(true);

        return t;
    }

    @Override
    public <V> V readValue(Thing<V> thing) {
        return thing.getValue();
    }
}
