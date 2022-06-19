package inventory.model.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PictureDto implements Serializable {

    private String contentType;
    private byte[] data;

}
