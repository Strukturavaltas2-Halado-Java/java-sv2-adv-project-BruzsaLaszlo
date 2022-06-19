package inventory.model.dtos;

import inventory.model.enums.ThingType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ThingDto implements Serializable {

    private Long id;
    private ThingType type;
    private List<PictureDto> pictures;
    private String description;
    private LocalDateTime updated;

    private LocationWithoutThingsDto location;

}


