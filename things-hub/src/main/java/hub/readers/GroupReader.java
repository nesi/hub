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

package hub.readers;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import hub.actions.UserUtils;
import hub.types.dynamic.Group;
import hub.types.persistent.Person;
import hub.types.persistent.Role;
import rx.Observable;
import rx.Subscriber;
import things.thing.*;
import things.types.TypeRegistry;
import things.utils.MatcherUtils;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by markus on 28/05/14.
 */
public class GroupReader extends AbstractThingReader {

    private ThingControl tc;
    private UserUtils userUtils;
    private TypeRegistry tr;


    private Observable.Operator<Thing<Group>, Map<String, Collection<Thing<Role>>>> roleToGroupTransformer;
    public GroupReader() {
        roleToGroupTransformer = subscriber -> new Subscriber<Map<String, Collection<Thing<Role>>>>() {
            @Override
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }

            @Override
            public void onNext(Map<String, Collection<Thing<Role>>> roleMap) {
                Set<Thing<Group>> groups = createGroups(roleMap);
                groups.stream().forEach(t -> subscriber.onNext(t));
            }
        };
    }


    @Override
    public Observable<? extends Thing<?>> findAllThings() {

        Observable<Thing<Role>> allRoles = tc.observeThingsForType(Role.class, false);

        Observable<Map<String, Collection<Thing<Role>>>> roleMap = allRoles.toMultimap(roleThing -> roleThing.getKey());

        return roleMap.lift(roleToGroupTransformer);
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForTypeAndKey(String type, String key) {

        Observable<Thing<Role>> matchingRoles = tc.observeThingsForTypeAndKey(Role.class, key, false);

        Observable<Map<String, Collection<Thing<Role>>>> roleMap = matchingRoles.toMultimap(roleThing -> roleThing.getKey());

        return roleMap.lift(roleToGroupTransformer);

    }

    @Override
    public Observable<? extends Thing<?>> findThingsForTypeMatchingKey(String type, String key) {
        Observable<Thing<Role>> matchingRoles = tc.observeThingsForTypeMatchingKey(Role.class, key, false);

        Observable<Map<String, Collection<Thing<Role>>>> roleMap = matchingRoles.toMultimap(roleThing -> roleThing.getKey());

        return roleMap.lift(roleToGroupTransformer);
    }

    @Override
    public Observable<? extends Thing<?>> findThingForId(String id) {
        return Observable.empty();
    }

    @Override
    public Observable<? extends Thing<?>> findThingsMatchingTypeAndKey(String typeMatcher, String keyMatcher) {

        Preconditions.checkArgument(MatcherUtils.wildCardMatch(tr.getType(Group.class), typeMatcher), "Group reader can not deal with type matcher: "+typeMatcher);

        Observable<Thing<Role>> matchingRoles = tc.observeThingsForTypeMatchingKey(Role.class, keyMatcher, false);

        return matchingRoles.toMultimap(roleThing -> roleThing.getKey()).lift(roleToGroupTransformer);

    }

    @Override
    public Observable<? extends Thing<?>> getChildrenForId(String id) {
        return Observable.empty();
    }

    @Override
    public Observable<? extends Thing<?>> getChildrenMatchingTypeAndKey(Observable<? extends Thing<?>> things, String typeMatcher, String keyMatcher) {

        return Observable.empty();
    }


    private Set<Thing<Group>> createGroups(Map<String, Collection<Thing<Role>>> roleMap) {

        Set<Thing<Group>> groups = Sets.newTreeSet();

        for ( String groupName : roleMap.keySet() ) {

            Group g = new Group(groupName);
            for ( Thing<Role> role : roleMap.get(groupName) ) {
                Observable<Thing<Person>> persons = tc.observeParentsOfType(role, Person.class, true);
                persons.map(p -> userUtils.createUser(p)).toBlockingObservable().forEach(userThing -> {
                    g.addMember(userThing.getValue());
                });
            }

            Thing<Group> group = new Thing();
            group.setThingType(tr.getType(Group.class));
            group.setId(groupName);
            group.setKey(groupName);
            group.setValue(g);
            group.setValueIsPopulated(true);

            groups.add(group);
        }

        return groups;

    }


    @Override
    public <V> V readValue(Thing<V> thing) {
        return null;
    }


    @Inject
    public void setTc(ThingControl tc) {
        this.tc = tc;
    }

    @Inject
    public void setTr(TypeRegistry tr) {
        this.tr = tr;
    }

    @Inject
    public void setUserUtils(UserUtils userUtils) {
        this.userUtils = userUtils;
    }
}
