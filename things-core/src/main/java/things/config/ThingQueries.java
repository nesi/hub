package things.config;

import com.google.common.collect.Maps;
import things.thing.ThingQuery;

import java.util.Map;

/**
 * @author: Markus Binsteiner
 */
public class ThingQueries {

    private final Map<String, ThingQuery> thingQueries = Maps.newHashMap();

    public ThingQueries() {
    }

    public void addQuery(String matcher, ThingQuery action) {

        thingQueries.put(matcher, action);

    }

    public ThingQuery get(String actionName) {

        return thingQueries.get(actionName);
    }

}
