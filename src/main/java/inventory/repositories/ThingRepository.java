package inventory.repositories;

import inventory.model.entities.Thing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ThingRepository extends JpaRepository<Thing, Long> {
}
