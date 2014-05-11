package things.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import things.thing.ThingControl;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner Date: 31/03/14 Time: 5:26 PM
 */
public class ThingModule extends SimpleModule {

    private final ThingControl tc;

    public ThingModule(ObjectMapper objectMapper, ThingControl tc) {
        this.tc = tc;
//        addDeserializer(Thing.class, new ThingDeserializer(objectMapper));
//        addSerializer(Thing.class, new ThingSerializer(tc));
    }


}
