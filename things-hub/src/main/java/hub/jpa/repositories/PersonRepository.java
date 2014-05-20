package hub.jpa.repositories;

import hub.types.persistent.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import things.jpa.ValueRepository;

/**
 * Created by markus on 20/05/14.
 */
public interface PersonRepository extends JpaRepository<Person, String>, ValueRepository<Person, String> {
}
