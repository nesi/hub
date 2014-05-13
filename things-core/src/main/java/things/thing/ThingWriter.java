package things.thing;

import java.util.Optional;

/**
 * @author: Markus Binsteiner
 */
public interface ThingWriter {

    default Thing<?> addChild(Thing<?> parent, Thing<?> child) {
        child.getParents().add(parent.getId());
        return saveThing(child);
    }

    abstract <V> Thing<V> saveThing(Thing<V> t);

    abstract Object saveValue(Optional valueId, Object value);

}
