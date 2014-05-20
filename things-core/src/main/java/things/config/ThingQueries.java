package things.config;

import com.google.common.collect.Maps;
import things.thing.ThingQuery;

import java.util.Map;
import java.util.Set;

/**
 * @author: Markus Binsteiner
 */
public class ThingQueries {

    private final Map<String, ThingQuery> thingQueries = Maps.newHashMap();

    public ThingQueries() {
    }

    public void addQuery(ThingQuery action) {
        Set<String> matchers = action.getSupportedQueryNames();
        matchers.forEach(t -> thingQueries.put(t, action));
    }

    public ThingQuery get(String actionName) {

        return thingQueries.get(actionName);
    }

}
