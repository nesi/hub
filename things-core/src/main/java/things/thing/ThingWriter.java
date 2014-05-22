package things.thing;

import java.util.Optional;

/**
 * @author: Markus Binsteiner
 */
public interface ThingWriter {

    abstract boolean deleteThing(String id, Optional<String> type, Optional<String> key);

    abstract <V> Thing<V> saveThing(Thing<V> t);

    abstract <V> Thing<V> addChild(Thing<?> parent, Thing<V> child);

//    abstract Object saveValue(Optional valueId, Object value);

}
