package things.thing;

import java.util.Optional;

/**
 * @author: Markus Binsteiner
 */
public interface ThingWriter {

    abstract <V> Thing<V> saveThing(Thing<V> t);

    default Thing<?> addChild(Thing<?> parent, Thing<?> child) {
        child.getParents().add(parent.getId());
        return saveThing(child);
    }

    abstract boolean deleteThing(String id, Optional<String> type, Optional<String> key);

//    abstract Object saveValue(Optional valueId, Object value);

}
