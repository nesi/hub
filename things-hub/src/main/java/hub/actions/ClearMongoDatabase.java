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

package hub.actions;

import com.google.common.collect.ImmutableSet;
import hub.types.persistent.Person;
import hub.types.persistent.Role;
import hub.types.persistent.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import rx.Observable;
import things.thing.Thing;
import things.thing.ThingAction;

import java.util.Map;
import java.util.Set;

/**
 * @author: Markus Binsteiner
 */
public class ClearMongoDatabase implements ThingAction {

    private MongoTemplate mo;

    public ClearMongoDatabase() {
    }

    @Override
    public Observable<? extends Thing<?>> execute(String command, Observable<? extends Thing<?>> things, Map<String, String> parameters) {
        mo.dropCollection(Person.class);
        mo.dropCollection(Thing.class);
        mo.dropCollection(Username.class);
        mo.dropCollection(Role.class);

        return Observable.empty();
    }

    @Override
    public Set<String> getSupportedActionNames() {
        return ImmutableSet.<String>of("clear_mongo");
    }

    @Autowired
    public void setMongoTemplate(MongoTemplate mo) {
        this.mo = mo;
    }
}
