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

    Iterable<Thing<?>> findByThingType(String type);

    @Query("select t from Thing t WHERE t.thingType = :thingType and t.key = :thingKey")
    Iterable<Thing<?>> findByTypeAndKey(@Param("thingType") String thingType, @Param("thingKey") String thingKey);

    @Query("select child from Thing child where :otherKey = child.key and :otherType = child.thingType and :parentId MEMBER OF child.parents")
    Iterable<Thing<?>> findChildrensByTypeAndKey(@Param("parentId") String parentId, @Param("otherType") String type, @Param("otherKey") String key);

    @Query("select t from Thing t WHERE t.thingType = :thingType and t.key = :thingKey")
    Iterable<Thing<?>> findMatchingTypeAndKey(@Param("thingType") String thingType, @Param("thingKey") String thingKey);
}
