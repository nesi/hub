package hub.auth;

import com.google.common.collect.Sets;
import hub.backends.users.types.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * Created by markus on 8/07/14.
 */
public class HubUserDetails implements UserDetails {

    private static Collection<? extends GrantedAuthority> generateAuthorities(Person p) {

		Set<GrantedAuthority> auths = Sets.newHashSet();

		for (final String group : p.getRoles().keySet()) {

			for (final String role : p.getRoles().get(group)) {
				GrantedAuthority ga = () -> "ROLE_" + group + "_" + role;
				auths.add(ga);
			}
		}

		return auths;
	}

    private Person person;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> auths;

    public HubUserDetails(Person p, String username, String password) {
        this.person = p;
        this.username = username;
        this.password = password;
        this.auths = generateAuthorities(this.person);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return auths;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
