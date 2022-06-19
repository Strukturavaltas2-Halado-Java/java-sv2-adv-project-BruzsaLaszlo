package inventory.model.entities;

import inventory.model.enums.ThingType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "things")
@NoArgsConstructor
@Getter
@Setter
public class Thing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Location location;

    @Enumerated(EnumType.STRING)
    private ThingType type;

    @ElementCollection(fetch = FetchType.LAZY)
    @JoinTable(name = "pictures", joinColumns = @JoinColumn(name = "thing_id"))
    private List<Picture> pictures = new ArrayList<>();

    private String description;

    private LocalDateTime updated;

    public void addPicture(String contentType, byte[] data) {
        pictures.add(new Picture(contentType, data));
    }

    public Thing(Location location, ThingType type, String description) {
        this.location = location;
        this.type = type;
        this.description = description;
    }

    @PrePersist
    void setUpdated() {
        updated = LocalDateTime.now();
    }

}
