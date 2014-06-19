package hub.readers;

import hub.actions.UserManagement;
import hub.types.dynamic.PersonProperty;
import rx.Observable;
import things.thing.AbstractSimpleThingReader;
import things.thing.AbstractThingReader;
import things.thing.Thing;
import things.types.TypeRegistry;

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
        return Observable.empty();
    }

    @Override
    public Observable<? extends Thing<?>> getChildrenForId(String id) {
        return null;
    }

    @Override
    public Observable<? extends Thing<?>> getChildrenMatchingTypeAndKey(Observable<? extends Thing<?>> things, String typeMatcher, String keyMatcher) {
        return null;
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForTypeAndKey(String type, String key) {
        return null;
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForTypeMatchingKey(String type, String key) {
        return null;
    }

    private Thing<PersonProperty> wrapPersonProperty(PersonProperty p) {
        Thing t = new Thing();
        t.setThingType(tr.getType(PersonProperty.class));
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
