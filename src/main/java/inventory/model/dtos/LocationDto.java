package inventory.model.dtos;

import inventory.model.enums.Room;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LocationDto implements Serializable {

    private Long id;
    private String name;
    private Room room;
    private String info;
    private List<ThingWithoutLocationDto> things;

}
