package things.thing;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 31/03/14
 * Time: 12:28 PM
 */
public class ThingDeserializer extends JsonDeserializer<Thing> {

    private final ObjectMapper objectMapper;

    public ThingDeserializer(ObjectMapper om) {
        this.objectMapper = om;
    }

    @Override
    public Thing deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

//        ObjectCodec oc = jp.getCodec();
//        JsonNode node = null;
//        node = oc.readTree(jp);
//
//        JsonNode keyNode = node.get("key");
//        String key = null;
//        if ( keyNode != null ) {
//            key = keyNode.asText();
//        }
//
//        JsonNode typeNode = node.get("type");
//        String type = null;
//        if ( typeNode != null ) {
//            type = typeNode.asText();
//        }
//
//        String id = null;
//        JsonNode idNode = node.get("id");
//        if ( idNode != null ) {
//            id = idNode.asText();
//        }
//
//        Thing t = new Thing();
//        t.setKey(key);
//        t.setId(id);
//        t.setThingType(type);
//
//        JsonNode valueNode = node.get("value");
//        if ( valueNode != null ) {
//                Object p = objectMapper.treeToValue(valueNode, TypeUtil.getTypeClass(type));
//                t.setValue(p);
//        }
//
//        return t;
        return null;
    }
}
