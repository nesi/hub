package hub.backends.users.repositories;

import hub.backends.users.types.Identity;
import org.springframework.data.jpa.repository.JpaRepository;
import things.jpa.ValueRepository;

import java.util.Iterator;
import java.util.List;

/**
 * Created by markus on 23/06/14.
 */
public interface IdentityRepository extends JpaRepository<Identity, String>, ValueRepository<Identity, String> {

    public List<Identity> findByAlias(String uniqueUsername);

    public List<Identity> findByResearcherId(Integer i);

    public List<Identity> findByAdviserId(Integer i);
}
