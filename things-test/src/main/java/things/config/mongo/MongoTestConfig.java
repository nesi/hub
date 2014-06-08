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

package things.config.mongo;

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
import things.mongo.MongoConnector;

/**
 * Created by markus on 21/05/14.
 */
@Configuration
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, MongoTemplateAutoConfiguration.class, MongoRepositoriesAutoConfiguration.class, MongoAutoConfiguration.class})
@ComponentScan({"things.thing", "things.config.generic"})
public class MongoTestConfig extends MongoConfig {

    @Override
    protected String getDatabaseName() {
        return "testRuns";
    }

    @Bean
    public MongoConnector mongoConnector() throws Exception {
        MongoConnector mc = new MongoConnector(mongoTemplate());
        return mc;
    }
}
