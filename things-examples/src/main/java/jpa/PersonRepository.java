package jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import things.jpa.ValueRepository;
import types.Person;

/**
 * Created by markus on 20/05/14.
 */
public interface PersonRepository extends JpaRepository<Person, String>, ValueRepository<Person, String> {
}
