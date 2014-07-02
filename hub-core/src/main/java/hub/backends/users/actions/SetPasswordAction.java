package hub.backends.users.actions;

import com.google.common.collect.ImmutableSet;
import hub.backends.users.queries.UsernameQuery;
import hub.backends.users.repositories.PasswordRepository;
import hub.backends.users.types.Password;
import hub.backends.users.types.Person;
import hub.backends.users.types.Username;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rx.Observable;
import things.exceptions.ThingRuntimeException;
import things.thing.Thing;
import things.thing.ThingAction;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by markus on 23/06/14.
 */
public class SetPasswordAction implements ThingAction {

    @Inject
    private PasswordRepository passwordRepository;

    @Inject
    private UsernameQuery usernameQuery;

    @Inject
    private BCryptPasswordEncoder encoder;

    @Override
    public Observable<? extends Thing<?>> execute(String actionName, Observable<? extends Thing<?>> things, Map<String, String> parameters) {

        Password pw = things.map(t -> (Password)t.getValue()).toBlockingObservable().single();

        String service = pw.getService();
        String username = pw.getUsername();

        Username un = new Username(service, username);

        // this also checks whether a person exists and throws an exception otherwise
        Observable<Thing<Person>> tp = usernameQuery.findPersonForUsername(un);

        try {
            Person p = tp.toBlockingObservable().single().getValue();
        } catch (Exception e) {
            throw new ThingRuntimeException("Could not find a person who has username '"+username+"' at service '"+service);
        }

        //TODO check whether current user is allowed to set password

        List<Password> existing = passwordRepository.findByServiceAndUsername(service, username);

        if ( existing.size() > 1 ) {
            throw new ThingRuntimeException("More than one passwords exist for service '"+service+"' and username '"+username+"'");
//        } else if ( existing.size() == 1 ) {
//            pw.setId(existing.get(0).getId());
        }

        String hashedPassword = encoder.encode(pw.getPassword());
        pw.setPassword(hashedPassword);

        Password temp = passwordRepository.saveAndFlush(pw);

        return Observable.empty();
    }

    @Override
    public Set<String> getSupportedActionNames() {
        return ImmutableSet.<String>builder().add("set_password").build();
    }
}
