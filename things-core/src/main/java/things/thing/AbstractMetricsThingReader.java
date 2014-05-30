package things.thing;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.google.common.collect.Maps;

import javax.inject.Inject;
import java.util.Map;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * Created by markus on 30/05/14.
 */
abstract public class AbstractMetricsThingReader extends AbstractThingReader {

    @Inject
    protected MetricRegistry metrics = null;

    private Map<String, Timer> timers = Maps.newHashMap();

    protected synchronized Timer getTimer(String timerName) {
        if ( timers.get(timerName) == null ) {
            com.codahale.metrics.Timer temp = metrics.timer(name(this.getClass(), timerName));
            timers.put(timerName, temp);
        }
        return timers.get(timerName);
    }

    protected Timer.Context initContext(String timerName) {
        return getTimer(timerName).time();
    }
}
