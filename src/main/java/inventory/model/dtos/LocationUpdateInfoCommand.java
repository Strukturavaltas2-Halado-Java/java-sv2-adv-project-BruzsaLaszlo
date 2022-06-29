package inventory.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LocationUpdateInfoCommand implements Serializable {

    @Schema(description = "info", example = "feljött a talajvíz")
    private String info;

}
