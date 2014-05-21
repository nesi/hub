package things.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import things.types.UniqueKeyType;

/**
 * Created by markus on 21/05/14.
 */
public interface UniqueKeyTypeRepository extends JpaRepository<UniqueKeyType, String>, ValueRepository<UniqueKeyType, String> {


}
