package things.config;

import com.google.common.collect.Maps;
import things.thing.ThingQuery;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Collection class that holds all {@link things.thing.ThingQuery}s for this instance.
 *
 * Every query needs to be mapped to an query name, so the framework knows how to
 * execute it. One {@link things.thing.ThingQuery}-class can be mapped multiple times
 * to different names, the implementing class itself can then check which name it was called under.
 *
 * Execution of queries happens in {@link things.thing.ThingControlMinimal#executeQuery(String, rx.Observable, java.util.Map)}.
 */
public class ThingQueries {

    private final Map<String, ThingQuery> thingQueries = Maps.newHashMap();

    public ThingQueries() {
    }


    /**
     * Add a new query.
     */
    public void addQuery(ThingQuery query) {
        Set<String> matchers = query.getSupportedQueryNames();
        matchers.forEach(t -> thingQueries.put(t, query));
    }

    /**
     * Gets the action that supports the specified name.
     */
    public Optional<ThingQuery> get(String actionName) {

        return Optional.ofNullable(thingQueries.get(actionName));
    }

}
