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

package things.thing;

import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import things.config.mongo.MongoTestConfig;
import things.mongo.MongoConnector;
import things.types.NoRestrictionsType;
import things.types.UniqueKeyType;

import javax.inject.Inject;

/**
 * Created by markus on 21/05/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = {MongoTestConfig.class})
public class MongoThingControlTest extends BaseThingControlTest {

    @Inject
    private MongoConnector connector;


    @Override
    public void deleteAllThings() {
        MongoTemplate mt = connector.getMongoTemplate();
        mt.dropCollection(Thing.class);
        mt.dropCollection(NoRestrictionsType.class);
        mt.dropCollection(UniqueKeyType.class);
    }
}
