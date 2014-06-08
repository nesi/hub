/*
 * Things
 *
 * Copyright (c) 2014, Markus Binsteiner. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */

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
