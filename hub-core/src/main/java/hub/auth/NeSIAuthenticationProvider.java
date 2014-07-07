package hub.auth;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import hub.Constants;
import hub.backends.users.UserManagement;
import hub.backends.users.actions.CheckPasswordQuery;
import hub.backends.users.types.Password;
import hub.backends.users.types.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import things.thing.Thing;
import things.thing.ThingsObjectMapper;
import things.types.AnnotationTypeFactory;
import things.types.TypeRegistry;

import javax.inject.Inject;
import java.util.*;

/**
 * Project: project_management
 * <p/>
 * Written by: Markus Binsteiner Date: 11/12/13 Time: 1:40 PM
 */
public class NeSIAuthenticationProvider implements
		AuthenticationProvider {

    private static Logger myLogger = LoggerFactory.getLogger(NeSIAuthenticationProvider.class);

	private final Map<String, String> users = Maps.newHashMap();
	private final RestTemplate restTemplate = new RestTemplate();
	private final TypeRegistry tr;

    @Autowired
    private CheckPasswordQuery checkPasswordQuery;

    @Autowired
    private UserManagement userManagement;


	public NeSIAuthenticationProvider() {

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();

		tr = AnnotationTypeFactory.createTypeRegistry();

		ThingsObjectMapper tom = new ThingsObjectMapper(tr);
		jsonMessageConverter.setObjectMapper(tom);
		messageConverters.add(jsonMessageConverter);
		restTemplate.setMessageConverters(messageConverters);

	}

	@Override public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		String username = (String) authentication.getPrincipal();

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

			UsernamePasswordAuthenticationToken token = generateToken(
					person, password);

			return token;

		} catch (Exception e) {
            throw new AuthenticationServiceException("Authentication failed.", e);
		}
	}

	private UsernamePasswordAuthenticationToken generateToken(Person p, String pw) {

		Set<GrantedAuthority> auths = Sets.newHashSet();

		for ( final String group : p.getRoles().keySet() ) {

			for ( final String role : p.getRoles().get(group) ) {
				GrantedAuthority ga = new GrantedAuthority() {
					@Override public String getAuthority() {
						return "ROLE_"+group+"_"+role;
					}
				};
				auths.add(ga);
			}
		}

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(p.getAlias(), pw, auths);
		return token;
	}

	@Override public boolean supports(Class<?> authentication) {
		if (authentication.equals(UsernamePasswordAuthenticationToken.class)) {
			return true;
		} else {
			return false;
		}
	}
}
