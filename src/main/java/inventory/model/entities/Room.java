package inventory.model.entities;

import inventory.model.enums.RoomName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoomName name;

    private String description;

    @OneToMany(mappedBy = "room")
    private List<Thing> things = new ArrayList<>();


    public void addThing(Thing thing) {
        things.add(thing);
        thing.setRoom(this);
    }
}
