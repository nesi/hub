package rooms.types.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rooms.types.Bridge;
import rooms.types.Profile;
import things.jpa.ValueRepository;

/**
 * Created by markus on 20/05/14.
 */
public interface ProfileRepository extends JpaRepository<Profile, String>, ValueRepository<Profile, String> {
}
