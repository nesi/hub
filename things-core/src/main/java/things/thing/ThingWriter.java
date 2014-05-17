package things.thing;

/**
 * @author: Markus Binsteiner
 */
public interface ThingWriter {

    abstract <V> Thing<V> saveThing(Thing<V> t);

    default Thing<?> addChild(Thing<?> parent, Thing<?> child) {
        child.getParents().add(parent.getId());
        return saveThing(child);
    }

//    abstract Object saveValue(Optional valueId, Object value);

}
