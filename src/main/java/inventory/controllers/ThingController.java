package inventory.controllers;


import inventory.model.dtos.ThingCreateCommand;
import inventory.model.dtos.ThingDto;
import inventory.model.dtos.ThingUpdateDescriptionCommand;
import inventory.model.dtos.ThingUpdateLocationCommand;
import inventory.model.enums.ThingType;
import inventory.services.ThingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/things")
@RequiredArgsConstructor
public class ThingController {

    private final ThingService service;


    @GetMapping
    @ResponseStatus(OK)
    @Operation(summary = "Get things, filtered by description and type")
    @ApiResponse(
            responseCode = "200",
            description = "Things, filtered by description and type",
            content = @Content(mediaType = "application/json"))
    public List<ThingDto> findAllThing(
            @RequestParam Optional<String> description,
            @RequestParam Optional<ThingType> type
    ) {
        return service.getThings(description, type);
    }


    @PostMapping
    @ResponseStatus(CREATED)
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


    @PutMapping("/{id}/location")
    @ResponseStatus(ACCEPTED)
    @Operation(summary = "Update thing location by id")
    @ApiResponse(
            responseCode = "202",
            description = "Thing location has been updated",
            content = @Content(mediaType = "application/json"))
    public ThingDto updateThingLocation(
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


    @PostMapping("/{id}/picture")
    @ResponseStatus(CREATED)
    public void addPictureToThing(
            @PathVariable long id,
            @RequestPart("file") MultipartFile file
    ) {
        service.addPicture(id, file);
    }

    @GetMapping("/{id}")
    public ThingDto findThingById(@PathVariable(name = "id") long id) {
        return service.findById(id);
    }

}
