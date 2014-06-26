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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.base.Strings;
import things.exceptions.ThingRuntimeException;

import java.io.IOException;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner Date: 31/03/14 Time: 5:33 PM
 */
public class ThingSerializer extends JsonSerializer<Thing> {


    public ThingSerializer() {

    }

    @Override
    public void serialize(Thing thing, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if ( !Strings.isNullOrEmpty(thing.getId()) ) {
            jgen.writeStringField("id", thing.getId());
        }
        jgen.writeStringField("key", thing.getKey());
        jgen.writeStringField("type", thing.getThingType());

        if (!  thing.getValueIsPopulated() ) {
            throw new ThingRuntimeException("Thing "+ thing.getId()+" not populated");
        }


        jgen.writeObjectField("value", thing.getValue());


//        List<String> ids = thing.getOtherThings();
//
//        if ( ids.size() > 0 ) {
//            jgen.writeArrayFieldStart("otherThings");
//            for (String id : ids) {
//                jgen.writeString(id);
//            }
//            jgen.writeEndArray();
//
//        }

        jgen.writeEndObject();
    }
}
