package inventory.model.dtos;

import inventory.model.enums.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationWithoutThingsDto {

    private Long id;
    private String name;
    private Room room;
    private String info;

}
