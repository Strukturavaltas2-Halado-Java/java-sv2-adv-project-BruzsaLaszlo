package inventory.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.io.IOException;
import java.net.URI;

public class PictureUploadException extends AbstractThrowableProblem {

    public PictureUploadException(IOException e) {
        super(
                URI.create("picture/io-exception"),
                "Picture upload failed",
                Status.UNSUPPORTED_MEDIA_TYPE,
                e.getMessage()
        );
    }
}
