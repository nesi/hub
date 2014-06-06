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

package things.jpa;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;
import things.thing.Thing;
import things.utils.MatcherUtils;

import javax.persistence.Query;
import java.util.List;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * Created by markus on 21/05/14.
 */
public class MysqlJpaConnector extends JpaConnector {

    private com.codahale.metrics.Timer find_matching_type_and_key_timer;

    @Override
    public Observable<? extends Thing<?>> findThingsMatchingTypeAndKey(String type, String key) {

        final Timer.Context context = find_matching_type_and_key_timer.time();

        try {

            String sqlQuery = "select * from things thing0_ where thing0_.thing_type regexp :thingType and thing0_.thing_key regexp :thingKey";

            Query q = entityManager.createNativeQuery(sqlQuery, Thing.class);
            q.setParameter("thingType", MatcherUtils.convertGlobToRegex(type));
            q.setParameter("thingKey", MatcherUtils.convertGlobToRegex(key));
            List<Thing<?>> result = q.getResultList();

            return Observable.from(result);
        } finally {
            context.stop();
        }

    }


    @Override
    public Observable<? extends Thing<?>> findThingsForTypeMatchingKey(String type, String key) {

        final Timer.Context context = find_matching_type_and_key_timer.time();

        try {

            String sqlQuery = "select * from things thing0_ where thing0_.thing_type = :thingType and thing0_.thing_key regexp :thingKey";

            Query q = entityManager.createNativeQuery(sqlQuery, Thing.class);
            q.setParameter("thingType", type);
            q.setParameter("thingKey", MatcherUtils.convertGlobToRegex(key));
            List<Thing<?>> result = q.getResultList();

            return Observable.from(result);
        } finally {
            context.stop();
        }

    }

    @Autowired
    @Override
    public void setMetricRegistry(MetricRegistry reg) {
        super.setMetricRegistry(reg);
        find_matching_type_and_key_timer = metrics.timer(name(MysqlJpaConnector.class, "find-matching-type-and-key"));


    }
}
