package inventory.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class LocationNotFoundException extends AbstractThrowableProblem {

    public LocationNotFoundException(long id) {
        super(
                URI.create("location/not-found"),
                "Location not found",
                Status.NOT_FOUND,
                "Location not found with id: " + id
        );
    }

}
