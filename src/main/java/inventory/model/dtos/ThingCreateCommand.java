package inventory.model.dtos;

import inventory.model.enums.ThingType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThingCreateCommand implements Serializable {

    @NotNull(message = "A helyszín ID-ja nem lehet null")
    @Schema(description = "a helyszín ID-ja", example = "1")
    private Long locationId;

    @NotNull(message = "A könnyebb kereshetőség érdekében tárgy típusa nem lehet null")
    @Schema(description = "a tárgy típusa (ENUM)", example = "HAND_TOOL")
    private ThingType type;

    @NotBlank(message = "A tárgynak kell legyen leírása különben nehéz lesz rákeresni")
    @Schema(description = "leírás ami a lehető legtöbb mindent tartalmaz a tárgyról",
            example = "power supply 12V/2A, DC-Jack diameter 5.5mm/2.1mm")
    private String description;

}
