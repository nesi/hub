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
import com.google.common.collect.Lists;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rx.Observable;
import things.exceptions.ActionException;
import things.exceptions.NoSuchThingException;
import things.exceptions.ThingException;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingUtils;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * @author: Markus Binsteiner
 */
@RestController
@RequestMapping(value = "/rest")
public class ExecuteRestController {

    private ThingControl thingControl;
    private ThingUtils thingUtils;

    @Inject
    public ExecuteRestController(ThingControl tc, ThingUtils tu) {
        this.thingControl = tc;
        this.thingUtils = tu;
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/{actionName}/everything", method = RequestMethod.POST)
    @Timed
    public List<Thing> executeAllThings(@PathVariable("actionName") String action, @RequestParam Map<String, String> actionParams) throws ActionException {

        Observable<? extends Thing<?>> things = thingControl.observeAllThings(false);

        Observable<? extends Thing<?>> handle = thingControl.executeAction(action, things, actionParams);

        return Lists.newArrayList(handle.toBlockingObservable().toIterable());
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/{actionName}/every/{type}", method = RequestMethod.POST)
    @Timed
    public List<Thing> executeAllThingsOfType(@PathVariable("actionName") String action, @PathVariable("type") String type, @RequestParam Map<String, String> actionParams) throws ActionException {

        Observable<? extends Thing<?>> things = thingControl.observeThingsForType(type, false);

        Observable<? extends Thing<?>> handle = thingControl.executeAction(action, things, actionParams);

        return Lists.newArrayList(handle.toBlockingObservable().toIterable());
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/{actionName}", method = RequestMethod.POST)
    @Timed
    public List<Thing> executeGetAction(@PathVariable("actionName") String actionName, @RequestParam Map<String, String> allRequestParams) throws ActionException {

        Observable<? extends Thing<?>> handle = thingControl.executeAction(actionName, Observable.empty(), allRequestParams);

        return Lists.newArrayList(handle.toBlockingObservable().toIterable());
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/{actionName}/{type}/{key}")
    @Timed
    public List<Thing> getUniqueThingForTypeAndKey(@PathVariable("actionName") String action, @PathVariable("type") String type, @PathVariable("key") String key, @RequestParam Map<String, String> actionParam) throws ThingException, NoSuchThingException, ActionException {

        Observable<? extends Thing<?>> thing = thingControl.observeUniqueThingMatchingTypeAndKey(type, key, false);

        Observable actionResult = thingControl.executeAction(action, thing, actionParam);

        return Lists.newArrayList(actionResult.toBlockingObservable().toIterable());
    }

}
