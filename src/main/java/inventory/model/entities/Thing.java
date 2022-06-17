package inventory.model.entities;

import inventory.model.enums.ThingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "things")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Thing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Room room;

    @Enumerated(EnumType.STRING)
    private ThingType type;

    private byte[] picture;

    private String description;

    private LocalDateTime updated;

    @PrePersist
    void setUpdated() {
        updated = LocalDateTime.now();
    }

}
