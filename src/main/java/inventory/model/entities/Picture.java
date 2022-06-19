package inventory.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Picture {

    private String contentType;

    @Lob
    private byte[] data;

}
