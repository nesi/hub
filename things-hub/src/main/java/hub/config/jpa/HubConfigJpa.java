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

package hub.config.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jolbox.bonecp.BoneCPDataSource;
import hub.actions.ProjectDbUtils;
import hub.actions.UserManagement;
import hub.actions.UserUtils;
import hub.jpa.repositories.PersonRepository;
import hub.readers.GroupReader;
import hub.readers.PersonReader;
import hub.types.dynamic.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate3.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import things.config.ThingReaders;
import things.config.ThingWriters;
import things.jpa.JpaConnector;
import things.jpa.MysqlJpaConnector;
import things.jpa.ValueRepositories;
import things.thing.ActionManager;
import things.thing.DefaultActionManager;
import things.thing.ThingControl;
import things.types.AnnotationTypeFactory;
import things.types.ThingType;
import things.types.TypeRegistry;
import things.utils.json.ThingsObjectMapper;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 11/05/14
 * Time: 2:16 PM
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@PropertySource("classpath:pan_audit.properties")
@PropertySource("classpath:projectdb.properties")
@PropertySource("classpath:sshJobLister.properties")
@PropertySource(value = "file:/etc/hub/hub.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:${HOME}/.hub/hub.properties", ignoreResourceNotFound = true)
@ComponentScan({"hub.config.connectors", "things.thing", "things.view.rest", "things.config.metrics"})
@EnableJpaRepositories(basePackages = {"things.jpa"})
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, DataSourceAutoConfiguration.class, MongoTemplateAutoConfiguration.class, MongoRepositoriesAutoConfiguration.class, MongoAutoConfiguration.class})
public class HubConfigJpa {

    @Autowired
    private Environment env;

//    @Bean(name = "thingDataSource")
//    public DataSource dataSource() {
//
//        MysqlDataSource ds = new MysqlDataSource();
//        ds.setDatabaseName();
//
//        dataSource.setDriverClassName(env.getRequiredProperty("spring.datasource.driverClassName"));
//        dataSource.setUrl(env.getRequiredProperty("spring.datasource.url"));
//        dataSource.setUsername(env.getRequiredProperty("spring.datasource.username"));
//        dataSource.setPassword(env.getRequiredProperty("spring.datasource.password"));
//
//        return dataSource;
//
//    }

    @Bean
    public ActionManager actionManager() {
        return new DefaultActionManager();
    }

    @Bean(destroyMethod = "close", name = "thingDataSource")
    public DataSource dataSource() {
//        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//        DataSource ds = builder.setType(EmbeddedDatabaseType.H2).build();
//        return ds;

        BoneCPDataSource dataSource = new BoneCPDataSource();

        dataSource.setDriverClass(env.getRequiredProperty("thingDB.db.driver"));
        dataSource.setJdbcUrl(env.getRequiredProperty("thingDB.db.url"));
        dataSource.setUsername(env.getRequiredProperty("thingDB.db.username"));
        dataSource.setPassword(env.getRequiredProperty("thingDB.db.password"));

        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(false);
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("things.thing", "hub.jpa.repositories", "hub.types.persistent");
        factory.setDataSource(dataSource());
        factory.setMappingResources("thing.hbm.xml");
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Bean
    public JpaConnector jpaConnector() {
        JpaConnector con = new MysqlJpaConnector();
        return con;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ThingsObjectMapper tom = new ThingsObjectMapper();
        return tom;
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        return new JettyEmbeddedServletContainerFactory();
    }

    @Bean
    public ThingControl thingControl() throws Exception {
        ThingControl tc = new ThingControl();
        return tc;
    }

    @Bean
    public ThingReaders thingReaders() throws Exception {

        ThingReaders tr = new ThingReaders();
//        tr.addReader("person/*", jpaConnector());
//        tr.addReader("typeClass/*", jpaConnector());
//        tr.addReader("role/*", jpaConnector());
        tr.addReader("person/*", personReader());
        tr.addReader("group/*", groupReader());
        return tr;
    }

    @Bean
    public ThingWriters thingWriters() throws Exception {

        ThingWriters tw = new ThingWriters();
        //tw.addWriter("person/*", jpaConnector());
        //tw.addWriter("typeClass/*", jpaConnector());
        //tw.addWriter("role/*", jpaConnector());
        //tw.addWriter("username/*", jpaConnector());
        return tw;
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory());
        return transactionManager;
    }

    @Bean
    public TypeRegistry typeRegistry() {
        TypeRegistry tr = new TypeRegistry();
        for ( ThingType tt : AnnotationTypeFactory.getAllTypes() ) {
            tr.addType(tt);
        }
        return tr;
    }


    @Bean
    public PersonReader personReader() {
        return new PersonReader();
    }
    @Bean
    public GroupReader groupReader() {
        return new GroupReader();
    }

    @Bean
    public UserUtils userUtils() {
        return new UserUtils();
    }

    @Bean
    public ProjectDbUtils projectUtils() {
        return new ProjectDbUtils();
    }

    @Bean
    public UserManagement userManagement() {
        return new UserManagement();
    }

    @Bean(name = "valueValidator")
    public Validator validator() {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator;
    }

    @Bean
    @Inject
    public ValueRepositories valueRepositories() {

        ValueRepositories vr = new ValueRepositories();
//        vr.addRepository(tr.getType(Person.class), pr);
        return vr;
    }
}
