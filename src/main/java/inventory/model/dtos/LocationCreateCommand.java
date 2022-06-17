package inventory.model.dtos;

import inventory.model.enums.Room;
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
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Room room;

    private String info;

}
