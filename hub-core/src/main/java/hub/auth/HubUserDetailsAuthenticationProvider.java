package hub.auth;

import com.google.common.collect.Lists;
import hub.Constants;
import hub.backends.users.UserManagement;
import hub.backends.users.actions.CheckPasswordQuery;
import hub.backends.users.types.Password;
import hub.backends.users.types.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import rx.Observable;
import things.thing.Thing;
import things.types.TypeRegistry;
import things.types.TypeRegistryImpl;

import java.util.List;

/**
 * Created by markus on 8/07/14.
 */
public class HubUserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private static Logger myLogger = LoggerFactory.getLogger(HubUserDetailsAuthenticationProvider.class);

    @Autowired
    private CheckPasswordQuery checkPasswordQuery;

    @Autowired
    private UserManagement userManagement;

    @Autowired
    private TypeRegistryImpl tr;


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        myLogger.debug("Authenticating: "+username);

		String password = (String) authentication.getCredentials();

		Password pw = new Password();
		pw.setPassword(password);
		pw.setPerson(username);
		pw.setService(Constants.HUB_SERVICE_NAME);

        Person person = null;
		try {

			Thing<Password> t = Thing.createThingPoJo(tr, null, pw);

			List<Thing<Password>> queryParam = Lists.newArrayList(t);

            Observable<? extends Thing<?>> p = checkPasswordQuery.findPerson(Constants.HUB_SERVICE_NAME, username, password);

            try {
                person = (Person) p.toBlockingObservable().single().getValue();
            } catch (Exception e) {
                throw new AuthenticationServiceException("Can't find user with provided username '"+username+"' password");
            }

            HubUserDetails ud = new HubUserDetails(person, username, password);
            return ud;

		} catch (Exception e) {
            throw new AuthenticationServiceException("Authentication failed.", e);
		}
    }
}
