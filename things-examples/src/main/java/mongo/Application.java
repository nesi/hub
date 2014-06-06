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

package mongo;

import com.mongodb.Mongo;
import org.springframework.data.mongodb.core.MongoTemplate;
import rx.Observable;
import things.config.ThingReaders;
import things.config.ThingWriters;
import things.mongo.MongoConnector;
import things.thing.Thing;
import things.thing.ThingControl;
import types.Address;
import types.Person;
import types.Role;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 10/05/14
 * Time: 11:37 PM
 */
public class Application {

    public static void main(String[] args) throws Exception {

        Mongo m = new Mongo();
        MongoTemplate mt = new MongoTemplate(m, "helloKitty");
        MongoConnector defaultConnector = new MongoConnector(mt);

        mt.dropCollection(Person.class);
        mt.dropCollection(Thing.class);
        mt.dropCollection(Address.class);
        mt.dropCollection(Role.class);


        ThingReaders tr = new ThingReaders();
        tr.addReader("person/*", defaultConnector);
        tr.addReader("role/*", defaultConnector);
        tr.addReader("address/*", defaultConnector);


        ThingWriters tw = new ThingWriters();
        tw.addWriter("person/*", defaultConnector);
        tw.addWriter("role/*", defaultConnector);
        tw.addWriter("address/*", defaultConnector);

        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        ThingControl tc = new ThingControl();
        tc.setThingReaders(tr);
        tc.setThingWriters(tw);
        tc.setValidator(validator);

        Person user = new Person();
        user.setFirstName("Person");
        user.setLastName("Name");

        Thing<Person> pt = tc.createThing("username", user);

        Address address = new Address();
        address.setCity("Auckland");
        address.setCountry("NZ");
        address.setNr(1);
        address.setStreet("Fleet street");

        Thing<Address> at = tc.createThing("home", address);

        tc.addChildThing(pt, at);

        Object id = pt.getId();
        System.out.println("ID: " + id);

        Role role1 = new Role("role1");
        Thing<Role> r1t = tc.createThing("group_1", role1);

        Role role2 = new Role("role2");
        Thing<Role> r2t = tc.createThing("group_1", role2);

        Role role3 = new Role("role3");
        Thing<Role> r3t = tc.createThing("group_2", role3);

        tc.addChildThing(pt, r1t);
        tc.addChildThing(pt, r2t);
        tc.addChildThing(pt, r3t);


        Observable<? extends Thing<?>> childs = tc.observeChildrenMatchingTypeAndKey(pt, "role", "*2*", true);

        childs.toBlockingObservable().forEach(t -> System.out.println(t));


        Observable<? extends Thing<?>> childs2 = tc.observeChildrenMatchingTypeAndKey(pt, "address", "*", true);

        childs2.toBlockingObservable().forEach(t -> System.out.println(t));


    }
}
