package inventory.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class ThingNotFoundException extends AbstractThrowableProblem {

    public ThingNotFoundException(long id) {
        super(
                URI.create("thing/not-found"),
                "Thing not found",
                Status.NOT_FOUND,
                "Thing not found with id: " + id
        );
    }

}
