package inventory.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;

@RestController
public class ErrorHandlerController implements ErrorController {


    @GetMapping("/error")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Operation(summary = "Resource not found")
    @ApiResponse(
            responseCode = "404",
            description = "Resource not found",
            content = @Content(mediaType = "application/json"))
    public Problem getErrorPath() {
        return Problem.builder()
                .withType(URI.create("resource/not-found"))
                .withStatus(Status.NOT_FOUND)
                .withDetail("A keresett oldal nem található")
                .build();
    }

}
