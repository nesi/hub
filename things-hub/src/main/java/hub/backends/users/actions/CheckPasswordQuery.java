package hub.backends.users.actions;

import com.google.common.collect.ImmutableSet;
import hub.backends.users.queries.UsernameQuery;
import hub.backends.users.repositories.PasswordRepository;
import hub.backends.users.types.Password;
import hub.backends.users.types.Username;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rx.Observable;
import things.exceptions.ThingRuntimeException;
import things.thing.Thing;
import things.thing.ThingQuery;

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

    @Override
    public Observable<? extends Thing<?>> execute(String actionName, Observable<? extends Thing<?>> things, Map<String, String> parameters) {

        Password pw = things.map(t -> (Password)t.getValue()).toBlockingObservable().single();

        String service = pw.getService();
        String username = pw.getUsername();

        List<Password> existing = passwordRepository.findByServiceAndUsername(service, username);

        if ( existing.size() > 1 ) {
            throw new ThingRuntimeException("More than one passwords exist for service '"+service+"' and key '"+username+"'");
        } else if ( existing.size() == 0 ) {
            throw new ThingRuntimeException("No password found for service '"+service+"' and key '"+username+"'");
        }

        Password dbPassword = existing.get(0);

        boolean match = encoder.matches(pw.getPassword(), dbPassword.getPassword());

        if ( ! match ) {
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
