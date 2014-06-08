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

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import things.thing.Thing;

/**
 * Created by markus on 19/05/14.
 */
public interface ThingRepository extends CrudRepository<Thing<?>, String> {

    Iterable<Thing<?>> findByKey(String key);

    Iterable<Thing<?>> findByParents(String parentId);

    @Query("select t from Thing t WHERE t.thingType = :thingType")
    Iterable<Thing<?>> findByThingType(@Param("thingType") String thingtype);

    @Query("select t from Thing t WHERE t.thingType = :thingType and t.key = :thingKey")
    Iterable<Thing<?>> findByTypeAndKey(@Param("thingType") String thingType, @Param("thingKey") String thingKey);

//    @Query(nativeQuery = true, value = "select thing0_.thing_id as thing_id1_2_, thing0_.thing_key as thing_ke2_2_, thing0_.thing_type as thing_ty3_2_, thing0_.value_link as value_li4_2_, thing0_.popluated as popluate5_2_ from things thing0_ where thing0_.thing_type regexp ?1 and thing0_.thing_key regexp ?2")
//    Iterable<Object[]> findMatchingTypeAndKey(String thingType, String thingKey);

    @Query("select child from Thing child where :otherKey = child.key and :otherType = child.thingType and :parentId MEMBER OF child.parents")
    Iterable<Thing<?>> findChildrensByTypeAndKey(@Param("parentId") String parentId, @Param("otherType") String type, @Param("otherKey") String key);

}
