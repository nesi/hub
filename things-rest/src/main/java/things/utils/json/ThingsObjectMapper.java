package things.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import things.thing.ThingControl;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner Date: 31/03/14 Time: 5:00 PM
 */
public class ThingsObjectMapper extends ObjectMapper {

    private final ThingControl tc;
    
    public ThingsObjectMapper(ThingControl tc) {
        this.tc = tc;
        registerModule(new GuavaModule());
        registerModule(new ThingModule(this, tc));
        registerModule(new JSR310Module());
        registerModule(new AfterburnerModule());
    }

    public ThingControl getThingControl() {
        return tc;
    }
}
