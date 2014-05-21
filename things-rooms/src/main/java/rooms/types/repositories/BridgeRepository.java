package rooms.types.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rooms.types.Bridge;
import things.jpa.ValueRepository;

/**
 * Created by markus on 20/05/14.
 */
public interface BridgeRepository extends JpaRepository<Bridge, String>, ValueRepository<Bridge, String> {
}
