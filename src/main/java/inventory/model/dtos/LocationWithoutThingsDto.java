package inventory.model.dtos;

import inventory.model.enums.Room;
import lombok.Data;

@Data
public class LocationWithoutThingsDto {

    private Long id;
    private String name;
    private Room room;
    private String info;

}
