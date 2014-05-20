package jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import things.jpa.ValueRepository;
import types.Address;
import types.Person;

/**
 * Created by markus on 20/05/14.
 */
public interface AddressRepository extends JpaRepository<Address, String>, ValueRepository<Address,String> {
}
