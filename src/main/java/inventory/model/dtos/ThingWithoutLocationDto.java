package inventory.model.dtos;

import inventory.model.enums.ThingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThingWithoutLocationDto implements Serializable {

    private Long id;
    private ThingType type;
    private List<PictureDto> pictures;
    private String description;
    private LocalDateTime updated;

}
