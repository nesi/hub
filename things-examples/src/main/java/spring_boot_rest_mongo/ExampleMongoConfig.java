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

package spring_boot_rest_mongo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import things.config.ThingActions;
import things.config.ThingQueries;
import things.config.ThingReaders;
import things.config.ThingWriters;
import things.config.mongo.MongoConfig;
import things.mongo.MongoConnector;
import things.thing.ThingControl;

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
@EnableAutoConfiguration
@ComponentScan({"things.thing", "things.view.rest"})
public class ExampleMongoConfig extends MongoConfig {

    @Bean
    public MongoConnector defaultConnector() throws Exception {
        MongoConnector mc = new MongoConnector(mongoTemplate());
        return mc;
    }

    @Override
    protected String getDatabaseName() {
        return "things-rest-test";
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        return new JettyEmbeddedServletContainerFactory();
    }

    @Bean
    ThingActions thingActions() throws Exception {
        ThingActions ta = new ThingActions();
        return ta;
    }

    @Bean
    public ThingControl thingControl() throws Exception {
        ThingControl tc = new ThingControl();
        return tc;
    }

    @Bean
    public ThingQueries thingQueries() {
        ThingQueries tq = new ThingQueries();
        return tq;
    }

    @Bean
    public ThingReaders thingReaders() throws Exception {

        ThingReaders tr = new ThingReaders();
        tr.addReader("person/*", defaultConnector());
        tr.addReader("role/*", defaultConnector());
        tr.addReader("address/*", defaultConnector());

        return tr;
    }

    @Bean
    public ThingWriters thingWriters() throws Exception {

        ThingWriters tw = new ThingWriters();
        tw.addWriter("person/*", defaultConnector());
        tw.addWriter("role/*", defaultConnector());
        tw.addWriter("address/*", defaultConnector());

        return tw;
    }

    @Bean(name = "valueValidator")
    public Validator validator() {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator;
    }
}
