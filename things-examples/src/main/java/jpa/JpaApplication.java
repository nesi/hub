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

package jpa;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import rx.Observable;
import things.thing.Thing;
import things.thing.ThingControl;
import types.Address;
import types.Person;
import types.Role;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 10/05/14
 * Time: 11:37 PM
 */
public class JpaApplication {

    public static void main(String[] args) throws Exception {


        AbstractApplicationContext context = new AnnotationConfigApplicationContext(JpaConfig.class);

        final ThingControl tc = (ThingControl) context.getBean("thingControl");


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
