package things.thing;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.base.Strings;

import java.io.IOException;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner Date: 31/03/14 Time: 5:33 PM
 */
public class ThingSerializer extends JsonSerializer<Thing> {

    private final ThingControl tc;

    public ThingSerializer(ThingControl tc) {
        this.tc = tc;
    }

    @Override
    public void serialize(Thing thing, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if ( ! Strings.isNullOrEmpty(thing.getId()) ) {
            jgen.writeStringField("id", thing.getId());
        }
        jgen.writeStringField("key", thing.getKey());
        jgen.writeStringField("type", thing.getThingType());

        Object value = tc.getValue(thing);


        jgen.writeObjectField("value", value);


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
