package hub.readers;

import hub.actions.UserUtils;
import hub.types.persistent.Person;
import rx.Observable;
import things.exceptions.ThingRuntimeException;
import things.thing.*;
import things.types.TypeRegistry;

import javax.inject.Inject;

/**
 * @author: Markus Binsteiner
 */
public class UserReader extends AbstractThingReader  {

    private ThingControl tc;
    private UserUtils userUtils;

    @Override
    public Observable<? extends Thing<?>> findAllThings() {
        return tc.observeThingsForType(Person.class, true).map(p -> userUtils.createUser(p));
    }

    @Override
    public Observable<? extends Thing<?>> findThingForId(String id) {

        return Observable.empty();
    }

    @Override
    public Observable<? extends Thing<?>> findThingsMatchingTypeAndKey(final String type,
                                                                       final String key) {
        return tc.observeThingsMatchingTypeAndKey(typeRegistry.getType(Person.class), key, true).map(p -> userUtils.createUser((Thing<Person>) p));
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
        return tc.observeThingsForTypeAndKey(typeRegistry.getType(Person.class), key, true).map(p -> userUtils.createUser((Thing<Person>)p));
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForTypeMatchingKey(String type, String key) {
        return tc.observeThingsForTypeMatchingKey(Person.class, key, true).map(p -> userUtils.createUser((Thing<Person>)p));
    }

    @Override
    public <V> V readValue(Thing<V> thing) {
        throw new ThingRuntimeException("User will always store value inline");
    }

    @Inject
    public void setTc(ThingControl tc) {
        this.tc = tc;
    }

    @Inject
    public void setUserUtils(UserUtils userUtils) {
        this.userUtils = userUtils;
    }


}
