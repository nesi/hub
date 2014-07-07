package hub.backends.users.actions;

import com.google.common.collect.ImmutableSet;
import hub.Constants;
import hub.backends.users.PersonReader;
import hub.backends.users.UserManagement;
import hub.backends.users.queries.UsernameQuery;
import hub.backends.users.repositories.PasswordRepository;
import hub.backends.users.types.Password;
import hub.backends.users.types.Person;
import hub.backends.users.types.Username;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rx.Observable;
import things.exceptions.ThingRuntimeException;
import things.thing.Thing;
import things.thing.ThingQuery;
import things.types.TypeRegistry;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by markus on 23/06/14.
 */
public class CheckPasswordQuery implements ThingQuery {

    @Inject
    private UsernameQuery usernameQuery;

    @Inject
    private PasswordRepository passwordRepository;

    @Inject
    private BCryptPasswordEncoder encoder;

    @Inject
    private UserManagement userManagement;

    @Inject
    private TypeRegistry typeRegistry;

    @Override
    public Observable<? extends Thing<?>> execute(String actionName, Observable<? extends Thing<?>> things, Map<String, String> parameters) {

        Password pw = things.map(t -> (Password) t.getValue()).toBlockingObservable().single();

        String service = pw.getService();
        String username = pw.getUsername();

        return findPerson(service, username, pw.getPassword());

    }

    public Observable<? extends Thing<?>> findPerson(String service, String username, String password) {



        List<Password> existing = passwordRepository.findByServiceAndUsername(service, username);

        if ( existing.size() > 1 ) {
            throw new ThingRuntimeException("More than one passwords exist for service '" + service + "' and key '" + username + "'");
        } else if ( existing.size() == 0 ) {

            // check whether an admin for the 'hub' service
            if ( Constants.HUB_SERVICE_NAME.equals(service) ) {
                Person admin = userManagement.getAllPersons().get(Constants.DEFAULT_ADMIN_USERNAME);
                //TODO store admin password encrypted?
                boolean usernameFound = false;
                for ( String un : admin.getUsernames().get(Constants.HUB_SERVICE_NAME) ) {
                    if ( un.equals(username) ) {
                        usernameFound = true;
                        break;
                    }
                }

                if ( !usernameFound ) {
                    throw new ThingRuntimeException("No admin username/password found for '" + username + "' found.");
                }

                if ( userManagement.isAdmin(username, password) ) {
                    return Observable.from(PersonReader.wrapPerson(typeRegistry, admin));
                }

            }

            throw new ThingRuntimeException("No password found for service '" + service + "' and key '" + username + "'");
        }

        Password dbPassword = existing.get(0);

        boolean match = encoder.matches(password, dbPassword.getPassword());

        if ( !match ) {
            return Observable.empty();
        } else {
            Username un = new Username(service, username);

            return usernameQuery.findPersonForUsername(un);
        }

    }

    @Override
    public Set<String> getSupportedQueryNames() {
        return ImmutableSet.<String>builder().add("password").build();
    }

}
