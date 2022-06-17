package inventory.model.entities;

import inventory.model.enums.Room;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Room room;

    private String info;

    @OneToMany(mappedBy = "location")
    @ToString.Exclude
    private List<Thing> things = new ArrayList<>();

    public void addThing(Thing thing) {
        things.add(thing);
        thing.setLocation(this);
    }
}
