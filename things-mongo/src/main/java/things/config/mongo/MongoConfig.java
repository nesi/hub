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

package things.config.mongo;

import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 11/05/14
 * Time: 4:59 PM
 */
@Configuration
public abstract class MongoConfig extends AbstractMongoConfiguration {

    @Override
    public Mongo mongo() throws Exception {
        return new Mongo("localhost");
    }

    @Bean
    @Override
    public MongoDbFactory mongoDbFactory() throws Exception {
        //UserCredentials userCredentials = new UserCredentials("joe", "secret");
        return new SimpleMongoDbFactory(mongo(), getDatabaseName());
    }

}
