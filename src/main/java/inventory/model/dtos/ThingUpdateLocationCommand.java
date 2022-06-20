package inventory.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThingUpdateLocationCommand {

    @NotNull
    @Schema(description = "a location ID-ja", example = "1")
    private Long locationId;

}
