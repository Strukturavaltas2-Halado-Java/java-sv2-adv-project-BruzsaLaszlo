package inventory.model.dtos;

import inventory.model.enums.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto implements Serializable {

    private Long id;
    private String name;
    private Room room;
    private String info;
    private List<ThingDto> things = new ArrayList<>();

}
