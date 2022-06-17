package inventory.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import inventory.model.enums.ThingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @JsonBackReference
    private Location location;

    @Enumerated(EnumType.STRING)
    private ThingType type;

    @ElementCollection(fetch = FetchType.LAZY)
    @JoinTable(name = "pictures", joinColumns = @JoinColumn(name = "thing_id"))
    private List<byte[]> pictures = new ArrayList<>();

    private String description;

    private LocalDateTime updated;

    public void addPicture(byte[] picture) {
        pictures.add(picture);
    }

    @PrePersist
    void setUpdated() {
        updated = LocalDateTime.now();
    }

}
