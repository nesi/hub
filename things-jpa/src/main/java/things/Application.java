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

package things;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import things.jpa.ThingRepository;
import things.thing.Thing;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by markus on 19/05/14.
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan("things.thing")
@EnableJpaRepositories(basePackages = "things.jpa")
public class Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
        ThingRepository repository = context.getBean(ThingRepository.class);

        // save a couple of things
        repository.save(new Thing("Jack", "Bauer"));
        repository.save(new Thing("Chloe", "O'Brian"));
        repository.save(new Thing("Kim", "Bauer"));
        repository.save(new Thing("David", "Palmer"));
        repository.save(new Thing("Michelle", "Dessler"));

        // fetch things by last name
        Iterable<Thing<?>> bauers = repository.findByKey("Dessler");
        System.out.println("Customer found with findByLastName('Bauer'):");
        System.out.println("--------------------------------------------");
        for ( Thing bauer : bauers ) {
            System.out.println(bauer);
        }
        Thing dessler = bauers.iterator().next();


        // fetch all things
        Iterable<Thing<?>> things = repository.findAll();
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for ( Thing customer : things ) {
            System.out.println(customer);
            customer.getParents().add(dessler.getId());
            customer.setValue("AAAAA");
            repository.save(customer);
        }
        System.out.println();

        things = repository.findAll();
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for ( Thing customer : things ) {
            customer.getParents().add(dessler.getId());
            repository.save(customer);
            System.out.println(customer);
        }
        System.out.println();

//        //fetch an individual customer by ID
//        Thing thing = repository.findOne(1L);
//        System.out.println("Customer found with findOne(1L):");
//        System.out.println("--------------------------------");
//        System.out.println(thing);
//        System.out.println();


        context.close();
    }

    @Bean
    public DataSource dataSource() {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("things.thing");
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();

        return factory.getObject();
    }
}
