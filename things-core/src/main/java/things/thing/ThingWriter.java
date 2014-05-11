package things.thing;

import java.util.Optional;

/**
 * @author: Markus Binsteiner
 */
public interface ThingWriter {

    abstract <V> Thing<V> saveThing(Thing<V> t);

    abstract Object saveValue(Optional valueId, Object value);

}
