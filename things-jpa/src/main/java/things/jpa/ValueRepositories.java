package things.jpa;

import com.google.common.collect.Maps;
import org.springframework.data.repository.CrudRepository;
import things.exceptions.TypeRuntimeException;

import java.util.Map;

/**
 * Created by markus on 20/05/14.
 */
public class ValueRepositories {

    private Map<String, ValueRepository> valueRepositories = Maps.newHashMap();

    public void addRepository(String type, ValueRepository repo) {
        valueRepositories.put(type, repo);
    }

    public ValueRepository get(String type) {
        ValueRepository repo = valueRepositories.get(type);

        if ( repo == null ) {
            throw new TypeRuntimeException("No value repository found for type: " + type);
        }

        return repo;
    }

}
