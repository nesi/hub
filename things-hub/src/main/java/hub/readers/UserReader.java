package hub.readers;

import hub.actions.UserUtils;
import hub.types.persistent.Person;
import rx.Observable;
import things.exceptions.ThingRuntimeException;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingReader;
import things.thing.TypeRegistry;

import javax.inject.Inject;

/**
 * @author: Markus Binsteiner
 */
public class UserReader implements ThingReader {

    private ThingControl tc;
    private UserUtils userUtils;

    @Inject
    public void setUserUtils(UserUtils userUtils) {
        this.userUtils = userUtils;
    }

    @Inject
    public void setTc(ThingControl tc) {
        this.tc = tc;
    }

    @Override
    public Observable<? extends Thing<?>> findThingsMatchingTypeAndKey(final String type,
                                                           final String key) {
        return tc.observeThingsMatchingTypeAndKey(TypeRegistry.getType(Person.class), key, true).map(p -> userUtils.createUser((Thing<Person>) p));
    }

    @Override
    public Observable<? extends Thing<?>> findAllThings() {
        return tc.observeThingsForType(Person.class, true).map(p -> userUtils.createUser(p));
    }

    @Override
    public <V> V readValue(Thing<V> thing) {
        throw new ThingRuntimeException("User will always store value inline");
    }
    

}
