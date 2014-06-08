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

package hub.config.connectors;

import hub.actions.ClearMongoDatabase;
import hub.actions.ImportRoleAndGroupAction;
import hub.actions.LdapImporter;
import hub.config.mongo.HubConfigMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import things.config.ThingActions;

/**
 * @author: Markus Binsteiner
 */
@Configuration
@Import(HubConfigMongo.class)
public class Actions {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public ClearMongoDatabase clearMongoDatabase() {
        return new ClearMongoDatabase();
    }

    @Bean
    public ImportRoleAndGroupAction importRoleAndGroupAction() throws Exception {
        ImportRoleAndGroupAction i = new ImportRoleAndGroupAction();
        return i;
    }

    @Bean
    public LdapImporter ldapImporter() throws Exception {
        LdapImporter ldapImporter = new LdapImporter();
        return ldapImporter;
    }

    @Bean
    ThingActions thingActions() throws Exception {
        ThingActions ta = new ThingActions();
        ta.addAction(ldapImporter());
        return ta;
    }

}
