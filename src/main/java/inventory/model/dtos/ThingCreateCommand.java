package inventory.model.dtos;

import inventory.model.enums.ThingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThingCreateCommand implements Serializable {

    private LocationDto room;
    private ThingType type;
    private byte[] picture;
    private String description;

}
