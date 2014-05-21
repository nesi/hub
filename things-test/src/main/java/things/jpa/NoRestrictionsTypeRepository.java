package things.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import things.types.NoRestrictionsType;

/**
 * Created by markus on 21/05/14.
 */
public interface NoRestrictionsTypeRepository extends JpaRepository<NoRestrictionsType, String>, ValueRepository<NoRestrictionsType, String> {
}
