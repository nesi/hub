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

package things.view.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingUtils;
import things.types.TypeRegistry;

import java.util.List;
import java.util.Optional;

/**
 * Created by markus on 19/05/14.
 */
@RestController
@RequestMapping(value = "/rest/get")
public class ParentsRestController {

    @Autowired
    private ThingControl thingControl;
    @Autowired
    private ThingUtils thingUtils;
    @Autowired
    private TypeRegistry typeRegistry;

    @Transactional(readOnly = true)
    @RequestMapping(value = "/parents/of/every/{type}/{key}/matching/{value}")
    public List<Thing> getParentsOfThingsMatchingTypeAndKeyAndValue(@PathVariable("type") String type, @PathVariable("key") String keyMatcher, @PathVariable("value") String stringValue) {

        Observable<? extends Thing<?>> result = thingControl.observeThingsMatchingKeyAndValueConvertedFromString(type, keyMatcher, stringValue);

        List<Thing> parents = thingControl.findParents(result, Optional.empty(), Optional.empty(), true);

        return parents;

    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/parents/of/every/{type}/matching/{value}")
    public List<Thing> getParentsOfThingsMatchingTypeAndValue(@PathVariable("type") String type, @PathVariable("value") String stringValue) {

        Observable<? extends Thing<?>> result = thingControl.observeThingsMatchingKeyAndValueConvertedFromString(type, "*", stringValue);

        List<Thing> parents = thingControl.findParents(result, Optional.empty(), Optional.empty(), true);

        return parents;

    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/parents/of/every/{type}/{key}")
    public List<Thing> getParentsOfThingsMatchingTypeAndKey(@PathVariable("type") String type, @PathVariable("key") String keyMatcher) {

        Observable<? extends Thing<?>> result = thingControl.observeThingsMatchingTypeAndKey(type, keyMatcher, false);

        List<Thing> parents = thingControl.findParents(result, Optional.empty(), Optional.empty(), true);
        return parents;

    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/parents/of/every/{type}")
    public List<Thing> getParentsOfThingsMatchingType(@PathVariable("type") String type) {

        Observable<? extends Thing<?>> result = thingControl.observeThingsMatchingType(type, false);

        List<Thing> parents = thingControl.findParents(result, Optional.empty(), Optional.empty(), true);
        return parents;

    }


}
