package inventory.model.entities;

import inventory.model.enums.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locations")
@NoArgsConstructor
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Room room;

    @Column(nullable = true)
    private String info;

    @OneToMany(mappedBy = "location", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<Thing> things = new ArrayList<>();

    public Location(String name, Room room) {
        this.name = name;
        this.room = room;
    }

    public Location(String name, Room room, String info) {
        this.name = name;
        this.room = room;
        this.info = info;
    }

    public void addThing(Thing thing) {
        things.add(thing);
        thing.setLocation(this);
    }
}
