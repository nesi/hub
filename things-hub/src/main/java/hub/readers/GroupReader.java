package hub.readers;

import hub.actions.UserUtils;
import hub.types.dynamic.Group;
import hub.types.persistent.Role;
import rx.Observable;
import things.thing.AbstractSimpleThingReader;
import things.thing.Thing;
import things.thing.ThingControl;
import things.types.TypeRegistry;

import javax.inject.Inject;

/**
 * Created by markus on 28/05/14.
 */
public class GroupReader extends AbstractSimpleThingReader {

    private ThingControl tc;
    private UserUtils userUtils;
    private TypeRegistry tr;


    @Override
    public Observable<? extends Thing<?>> findAllThings() {

        Observable<Thing<Role>> allRoles = tc.observeThingsForType(Role.class, false);

        return allRoles.map(r -> new Group(r.getKey())).distinct().map(g -> createGroupThing(g));

    }

    private Thing<Group> createGroupThing(Group g) {
        Thing t = new Thing();
        t.setThingType(tr.getType(g));
        t.setValue(g);
        t.setValueIsPopulated(true);
        t.setKey("n/a");
        return t;
    }

    @Override
    public <V> V readValue(Thing<V> thing) {
        return null;
    }


    @Inject
    public void setTc(ThingControl tc) {
        this.tc = tc;
    }

    @Inject
    public void setTr(TypeRegistry tr) {
        this.tr = tr;
    }

    @Inject
    public void setUserUtils(UserUtils userUtils) {
        this.userUtils = userUtils;
    }
}
