package hub.backends.users.repositories;

import hub.backends.users.types.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import things.jpa.ValueRepository;

import java.util.List;

/**
 * Created by markus on 25/06/14.
 */
public interface PasswordRepository extends JpaRepository<Password, String>, ValueRepository<Password, String> {


    List<Password> findByServiceAndUsername(String service, String person);
}
