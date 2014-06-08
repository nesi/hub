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

import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingUtils;
import things.types.TypeRegistry;

import java.util.List;

/**
 * Created by markus on 19/05/14.
 */
@RestController
@RequestMapping(value = "/rest/get")
public class ValueRestController {

    @Autowired
    private ThingControl thingControl;
    @Autowired
    private ThingUtils thingUtils;
    @Autowired
    private TypeRegistry typeRegistry;

    @Transactional(readOnly = true)
    @RequestMapping(value = "/every/{type}/{key}/matching/{value}")
    @Timed
    public List<Thing> getThingsOfTypeMatchingKeyAndValue(@PathVariable("type") String type, @PathVariable("key") String keyMatcher, @PathVariable("value") String stringValue) {

        List<Thing> result = thingControl.findThingsMatchingKeyAndValueConvertedFromString(type, keyMatcher, stringValue);
        return result;
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/every/{type}/matching/{value}")
    @Timed
    public List<Thing> getThingsOfTypeMatchingValue(@PathVariable("type") String type, @PathVariable("value") String stringValue) {

        List<Thing> result = thingControl.findThingsMatchingKeyAndValueConvertedFromString(type, "*", stringValue);
        return result;
    }


}
