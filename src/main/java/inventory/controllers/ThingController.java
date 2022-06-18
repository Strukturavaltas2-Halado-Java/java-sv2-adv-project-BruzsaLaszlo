package inventory.controllers;


import inventory.model.dtos.ThingCreateCommand;
import inventory.model.dtos.ThingDto;
import inventory.model.dtos.ThingUpdateDescriptionCommand;
import inventory.model.dtos.ThingUpdateLocationCommand;
import inventory.services.ThingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/things")
@RequiredArgsConstructor
public class ThingController {

    private final ThingService service;


    @GetMapping
    @Operation(summary = "Get all things")
    @ApiResponse(
            responseCode = "200",
            description = "All things",
            content = @Content(mediaType = "application/json"))
    public List<ThingDto> findAllThing() {
        return service.findAllThing();
    }


    @PostMapping
    @Operation(summary = "Create a thing")
    @ApiResponse(
            responseCode = "201",
            description = "Thing has been created",
            content = @Content(mediaType = "application/json"))
    public ThingDto createThing(@RequestBody @Valid ThingCreateCommand thingCreateCommand) {
        return service.createThing(thingCreateCommand);
    }


    @PutMapping("/{id}/description")
    @ResponseStatus(ACCEPTED)
    @Operation(summary = "Update thing description by id")
    @ApiResponse(
            responseCode = "202",
            description = "Thing description has been updated",
            content = @Content(mediaType = "application/json"))
    public ThingDto updateThingDescription(
            @PathVariable(name = "id") long id,
            @RequestBody @Valid ThingUpdateDescriptionCommand thingUpdateDescriptionCommand) {
        return service.updateThingDescription(id, thingUpdateDescriptionCommand);
    }


    @PutMapping("/{id}/thing")
    @ResponseStatus(ACCEPTED)
    @Operation(summary = "Update thing location by id")
    @ApiResponse(
            responseCode = "202",
            description = "Thing location has been updated",
            content = @Content(mediaType = "application/json"))
    public ThingDto updateThingthing(
            @PathVariable(name = "id") long id,
            @RequestBody @Valid ThingUpdateLocationCommand thingUpdatetLocationCommand) {
        return service.updateThingLocation(id, thingUpdatetLocationCommand);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete thing by id")
    @ApiResponse(
            responseCode = "204",
            description = "Thing has been deleted")
    public void deleteThingById(@PathVariable(name = "id") long id) {
        service.deleteThingById(id);
    }
}
