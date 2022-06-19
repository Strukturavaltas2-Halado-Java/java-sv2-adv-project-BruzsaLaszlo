package inventory.exceptions;

import inventory.model.enums.Room;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URI;

public class LocationCreateException extends AbstractThrowableProblem {

    public LocationCreateException(@NotBlank String name, @NotNull Room room) {
        super(
                URI.create("location/already-exist"),
                "Location already exist",
                Status.NOT_ACCEPTABLE,
                "Location already exist with name: %s and with room: %s ".formatted(name, room)
        );
    }

}
