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

import hub.actions.UserManagement;
import hub.types.dynamic.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import things.exceptions.ThingRuntimeException;
import things.thing.*;
import things.types.TypeRegistry;
import things.utils.MatcherUtils;

import javax.inject.Inject;

public class GroupReader extends AbstractThingReader {

    private final Logger myLogger = LoggerFactory.getLogger(GroupReader.class);

    @Inject
    private ThingControl tc;
    @Inject
    private UserManagement um;
    @Inject
    private TypeRegistry tr;


    @Override
    public Observable<? extends Thing<?>> findAllThings() {
        return Observable.from(um.getAllGroups().values()).map(g -> wrapGroup(g));
    }

    @Override
    public Observable<? extends Thing<?>> findThingForId(String id) {
        if ( ! id.startsWith("projectId:") ) {
            return Observable.empty();
        }

        try {
            String id_string = id.split(":")[1];
            Integer id_int = Integer.parseInt(id_string);

            String projectName = um.getGroup(id_int);
            Group g = um.getAllGroups().get(projectName);

            Thing<Group> groupThing = wrapGroup(g);

            return Observable.just(groupThing);

        } catch (Exception e) {
            throw new ThingRuntimeException("Can't parse group id "+id+": "+e.getLocalizedMessage());
        }
    }

    @Override
    public Observable<? extends Thing<?>> findThingsMatchingTypeAndKey(String type, String key) {
        return Observable.from(um.getAllGroups().values())
                .filter(g -> MatcherUtils.wildCardMatch(g.getGroupName(), key))
                .map(g -> wrapGroup(g));
    }

    @Override
    public Observable<? extends Thing<?>> getChildrenForId(String id) {
        return Observable.empty();
    }

    @Override
    public Observable<? extends Thing<?>> getChildrenMatchingTypeAndKey(Observable<? extends Thing<?>> things, String typeMatcher, String keyMatcher) {
        return Observable.empty();
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForTypeAndKey(String type, String key) {
        return Observable.from(um.getAllGroups().get(key)).map(g -> wrapGroup(g));
    }

    @Override
    public Observable<? extends Thing<?>> findThingsForTypeMatchingKey(String type, String key) {
        return findThingsMatchingTypeAndKey(type, key);
    }

    private Thing<Group> wrapGroup(Group g) {
        Thing t = new Thing();
        Integer id = um.getProjectId(g.getGroupName());
        if ( id != null ) {
            t.setId("projectId:"+id.toString());
        }
        t.setKey(g.getGroupName());
        t.setValue(g);
        t.setValueIsPopulated(true);
        t.setThingType(tr.getType(Group.class));

        return t;
    }

    @Override
    public <V> V readValue(Thing<V> thing) {
        // will always be populated
        return thing.getValue();
    }
}
