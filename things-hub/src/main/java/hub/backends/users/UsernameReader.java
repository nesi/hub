package hub.backends.users;

import com.google.common.collect.Sets;
import hub.backends.users.types.Person;
import hub.backends.users.types.Property;
import hub.backends.users.types.Username;
import rx.Observable;
import things.thing.AbstractThingReader;
import things.thing.Thing;
import things.types.TypeRegistry;
import things.utils.MatcherUtils;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

/**
 * Created by markus on 23/06/14.
 */
public class UsernameReader extends AbstractThingReader {

    @Inject
    private UserManagement um;

    @Inject
    private TypeRegistry tr;

    @Override
    public Observable<? extends Thing<?>> findAllThings() {
        return Observable.from(um.getAllPersons().values())
                .flatMap(p -> createUsernames(p, Optional.empty()));
    }

    private Observable<Thing<Username>> createUsernames(Person p, Optional<String> key) {

        Set<Property> usernames = p.getPropertiesForKey("linuxUsername");
        if ( usernames.size() == 0  ) {
            return Observable.empty();
        }

        return Observable.from(usernames)
                .filter(un -> MatcherUtils.wildCardMatch(un.getService(), key))
                .flatMap(un -> wrapUsername(un.getService(), un.getValue(), p.getAlias()));

    }

    private Observable<Thing<Username>> wrapUsername(String service, String un, String personId) {
        Thing t = new Thing();
        t.setValue(new Username(service, un));
        t.setThingType(tr.getType(Username.class));
        t.setValueIsPopulated(true);
        t.setKey(service);
        t.setParents(Sets.newHashSet(personId));
        return Observable.just(t);
    }

    @Override
    public Observable<? extends Thing<?>> findThingForId(String id) {
        return Observable.empty();
    }

    @Override
    public Observable<? extends Thing<?>> findThingsMatchingTypeAndKey(String type, String key) {
        return Observable.from(um.getAllPersons().values())
                .flatMap(p -> createUsernames(p, Optional.of(key)));
    }

    @Override
    public Observable<? extends Thing<?>> getChildrenForId(String id) {
        return Observable.just(um.getAllPersons().get(id))
                .flatMap(p -> createUsernames(p, Optional.empty()));
    }

    @Override
    public Observable<? extends Thing<?>> getChildrenMatchingTypeAndKey(Observable<? extends Thing<?>> things, String typeMatcher, String keyMatcher) {
        return things.filter(t -> tr.equals(Person.class, t.getThingType()))
                .map(t -> (Person) t.getValue())
                .flatMap(p -> createUsernames(p, Optional.of(keyMatcher)));
    }

    @Override
    public <V> V readValue(Thing<V> thing) {
        return thing.getValue();
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForTypeAndKey(String type, String key) {
        return Observable.from(um.getAllPersons().values())
                .flatMap(p -> createUsernames(p, Optional.of(key)));
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForTypeMatchingKey(String type, String key) {
        return Observable.from(um.getAllPersons().values())
                .flatMap(p -> createUsernames(p, Optional.of(key)));
    }
}
