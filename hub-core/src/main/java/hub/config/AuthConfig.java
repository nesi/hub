package hub.config;

import hub.auth.HubUserDetailsAuthenticationProvider;
import hub.auth.NeSIAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
@PropertySource(value = "file:/etc/hub/hub.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:${HOME}/.hub/hub.properties", ignoreResourceNotFound = true)
public class AuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    private HubUserDetailsAuthenticationProvider hubAuthenticationProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

        String prop = env.getProperty("enableAuth", "true");

        boolean enableAuth = Boolean.parseBoolean(prop);

        if ( enableAuth ) {
            http
                    .csrf().disable()
                    .httpBasic()
                    .and()
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .rememberMe();
        } else {
            http.csrf().disable();
        }

	}



	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {

		auth.authenticationProvider(hubAuthenticationProvider);

	}
}
