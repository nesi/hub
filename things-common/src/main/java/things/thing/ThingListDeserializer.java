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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import things.types.TypeRegistry;

import java.io.IOException;
import java.util.List;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 31/03/14
 * Time: 12:28 PM
 */
public class ThingListDeserializer extends JsonDeserializer<ThingList> {

    private final ObjectMapper objectMapper;
    private final TypeRegistry tr;

    public ThingListDeserializer(ObjectMapper om, TypeRegistry tr) {
        this.objectMapper = om;
        this.tr = tr;
    }

    @Override
    public ThingList deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        List<Thing> things = objectMapper.readValue(jp, new TypeReference<List<Thing>>() {});

        return new ThingList(things);
    }
}
