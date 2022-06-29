package inventory.repositories;

import inventory.model.entities.Location;
import inventory.model.enums.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("SELECT DISTINCT l.name FROM Location l")
    List<String> findAllNames();

    @Query("SELECT l.room FROM Location l WHERE l.name LIKE :name")
    List<Room> findAllRooms(@Param("name") String name);

}
