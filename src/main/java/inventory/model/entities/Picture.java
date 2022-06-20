package inventory.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Picture {

    private String contentType;

    @Lob
    private byte[] data;

}
