package hub.config.security;

import org.springframework.context.annotation.Configuration;

/**
 * Created by markus on 23/05/14.
 */
@Configuration
public class ShiroConfig {

//    @Bean
//public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//    return new LifecycleBeanPostProcessor();
//}
//
//@Bean
//public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
//    ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
//    factoryBean.setSecurityManager(securityManager);
//
//    String shiroIniContents = readFile(new ClassPathResource("shiro.ini").getFile().toPath());
//    factoryBean.setFilterChainDefinitions(shiroIniContents);//only reads the [urls] section
//
//    factoryBean.setLoginUrl("/rest/login");
//
//    return factoryBean;
//}
//
//
//@Bean
//public DefaultWebSecurityManager securityManager()  {
//    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//    securityManager.setRealm(stormpathRealm());
//    return securityManager;
//}
}
