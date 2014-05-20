package rooms.types.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rooms.types.Bridge;
import rooms.types.Light;
import things.jpa.ValueRepository;

/**
 * Created by markus on 20/05/14.
 */
public interface LightRepository extends JpaRepository<Light, String>, ValueRepository<Light, String> {
}
