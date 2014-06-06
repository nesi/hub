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
