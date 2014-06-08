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

package things.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import things.thing.ThingControl;

import javax.inject.Inject;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner Date: 31/03/14 Time: 5:00 PM
 */
public class ThingsObjectMapper extends ObjectMapper {

    private ThingControl tc;

    public ThingsObjectMapper() {
        registerModule(new GuavaModule());
        registerModule(new ThingModule(this, tc));
        registerModule(new JSR310Module());
        registerModule(new AfterburnerModule());
    }

    public ThingControl getThingControl() {
        return tc;
    }

    @Inject
    public void setThingControl(ThingControl tc) {
        this.tc = tc;
    }
}
