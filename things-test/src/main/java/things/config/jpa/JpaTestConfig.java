/*
 * Things
 *
 * Copyright (c) 2014, Markus Binsteiner. All rights reserved.
 *
 * Things is free software; you can redistribute it and/or
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

package things.config.jpa;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate3.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import things.config.generic.BaseTestConfigInMemory;
import things.jpa.JpaConnector;
import things.jpa.NoRestrictionsTypeRepository;
import things.jpa.UniqueKeyTypeRepository;
import things.jpa.ValueRepositories;
import things.types.NoRestrictionsType;
import things.types.TypeRegistry;
import things.types.UniqueKeyType;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by markus on 21/05/14.
 */
@Configuration
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, MongoTemplateAutoConfiguration.class, MongoRepositoriesAutoConfiguration.class, MongoAutoConfiguration.class})
@ComponentScan({"things.thing", "things.config.generic", "things.types"})
@EnableJpaRepositories(basePackages = {"things.jpa"})
public class JpaTestConfig extends BaseTestConfigInMemory {

    @Bean
    JpaConnector connector() {
        return new JpaConnector();
    }

    @Bean(name = "thingDataSource")
    public DataSource dataSource() throws Exception {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        DataSource ds = builder.setType(EmbeddedDatabaseType.H2).build();
        return ds;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() throws Exception {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("things.thing", "things.types");
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
    public PlatformTransactionManager transactionManager() throws Exception {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory());
        return transactionManager;
    }

    @Bean
    @Inject
    public ValueRepositories valueRepositories(TypeRegistry tr, NoRestrictionsTypeRepository nr, UniqueKeyTypeRepository ur) {

        ValueRepositories vr = new ValueRepositories();
        vr.addRepository(tr.getType(NoRestrictionsType.class), nr);
        vr.addRepository(tr.getType(UniqueKeyType.class), ur);
        return vr;
    }
}
