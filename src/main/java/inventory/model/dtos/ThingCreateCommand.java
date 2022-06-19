package inventory.model.dtos;

import inventory.model.enums.ThingType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ThingCreateCommand implements Serializable {

    @NotNull
    private Long locationId;

    private ThingType type;

    private String description;

    private byte[] picture;


    public ThingCreateCommand(Long locationId, ThingType type, String description) {
        this.locationId = locationId;
        this.type = type;
        this.description = description;
    }
}
