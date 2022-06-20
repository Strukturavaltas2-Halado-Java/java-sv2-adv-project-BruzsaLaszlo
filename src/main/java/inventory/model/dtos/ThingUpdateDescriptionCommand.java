package inventory.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThingUpdateDescriptionCommand {

    @NotNull
    @Schema(description = "leírás ami a lehető legtöbbet tartalmaz a tárgyról",
            example = "power supply 230V -> 12V/2A, DC-Jack diameter 5.5mm/2.1mm")
    private String description;

}
