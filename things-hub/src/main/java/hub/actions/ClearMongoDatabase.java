package hub.actions;

import hub.types.persistent.Person;
import hub.types.persistent.Role;
import hub.types.persistent.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import rx.Observable;
import things.thing.Thing;
import things.thing.ThingAction;

import java.util.Map;

/**
 * @author: Markus Binsteiner
 */
public class ClearMongoDatabase implements ThingAction {

    private MongoTemplate mo;

    public ClearMongoDatabase() {
    }

    @Autowired
    public void setMongoTemplate(MongoTemplate mo) {
        this.mo = mo;
    }

    @Override
    public String execute(String command, Observable<? extends Thing<?>> things, Map<String, String> parameters) {
        mo.dropCollection(Person.class);
        mo.dropCollection(Thing.class);
        mo.dropCollection(Username.class);
        mo.dropCollection(Role.class);

        return null;
    }
}
