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

package things.thing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import things.types.TypeRegistry;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner Date: 31/03/14 Time: 5:26 PM
 */
public class ThingModule extends SimpleModule {

    private final TypeRegistry tr;

    public ThingModule(ObjectMapper objectMapper, TypeRegistry tr) {
        this.tr = tr;
        addDeserializer(Thing.class, new ThingDeserializer(objectMapper, tr));
        addSerializer(Thing.class, new ThingSerializer());
        addDeserializer(ThingList.class, new ThingListDeserializer(objectMapper, tr));
    }


}
