package things.jpa;

/**
 * Created by markus on 20/05/14.
 */
public interface ValueRepository<V, ID> {

    V findOne(ID id);

    V save(V value);

}
