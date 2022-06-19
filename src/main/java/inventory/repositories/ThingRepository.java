package inventory.repositories;

import inventory.model.entities.Thing;
import inventory.model.enums.ThingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ThingRepository extends JpaRepository<Thing, Long> {

    @Query("""
            SELECT t
            FROM Thing t
            WHERE (:description IS NULL OR t.description LIKE concat('%',:description,'%')) AND
                  (:type IS NULL OR t.type = :type)
            """)
    List<Thing> getFilteredThings(Optional<String> description, Optional<ThingType> type);

}
