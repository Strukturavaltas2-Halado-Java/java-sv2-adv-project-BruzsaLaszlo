package inventory.model.dtos;

import inventory.model.enums.Room;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationCreateCommand implements Serializable {

    @NotBlank
    @Schema(description = "name of the location", example = "nyaraló")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Schema(description = "a helyiség neve", example = "pince")
    private Room room;

    @Schema(description = "info", example = "beletört a kulcs az ajtóba, nem zárható", nullable = true)
    private String info;

}
