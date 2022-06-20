package inventory.controllers;


import inventory.model.dtos.ThingCreateCommand;
import inventory.model.dtos.ThingDto;
import inventory.model.dtos.ThingUpdateDescriptionCommand;
import inventory.model.dtos.ThingUpdateLocationCommand;
import inventory.model.enums.ThingType;
import inventory.model.validation.FileSize;
import inventory.services.ThingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static inventory.model.validation.FileSize.BlobType.MEDIUMBLOB;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/things")
@RequiredArgsConstructor
@Validated
@Tag(name = "`Things` végpontok")
public class ThingController {

    private final ThingService service;


    @GetMapping
    @ResponseStatus(OK)
    @Operation(summary = "A tárgyak szűrése leírás and típus alapján")
    @ApiResponse(
            responseCode = "200",
            description = "A leírás and típus alapján szűrt tárgyak listája",
            content = @Content(mediaType = "application/json"))
    public List<ThingDto> findAllThing(
            @RequestParam @Parameter(example = "dugókulcs") Optional<String> description,
            @RequestParam @Parameter(example = "HAND_TOOL") Optional<ThingType> type
    ) {
        return service.findThingsByDescriptionAndType(description, type);
    }


    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Tárgy létrehozása")
    @ApiResponse(
            responseCode = "201",
            description = "A tárgy elmentve az adatbázisba",
            content = @Content(mediaType = "application/json"))
    public ThingDto createThing(@RequestBody @Valid ThingCreateCommand thingCreateCommand) {
        return service.createThing(thingCreateCommand);
    }


    @PutMapping("/{id}/description")
    @ResponseStatus(ACCEPTED)
    @Operation(summary = "A leírás frissítése a tárgy ID-ja alapján")
    @ApiResponse(
            responseCode = "202",
            description = "A leírás frissítése megtörtént",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(
            responseCode = "404",
            description = "A tárgy nem található",
            content = @Content(mediaType = "application/problem+json"))
    public ThingDto updateThingDescription(
            @PathVariable(name = "id") long id,
            @RequestBody @Valid ThingUpdateDescriptionCommand thingUpdateDescriptionCommand
    ) {
        return service.updateThingDescription(id, thingUpdateDescriptionCommand);
    }


    @PutMapping("/{id}/location")
    @ResponseStatus(ACCEPTED)
    @Operation(summary = "A tárgy helyének módosítása")
    @ApiResponse(
            responseCode = "202",
            description = "A tárgy helye módosítva lett",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(
            responseCode = "404",
            description = "A tárgy nem található",
            content = @Content(mediaType = "application/problem+json"))
    public ThingDto updateThingLocation(
            @PathVariable(name = "id") long id,
            @RequestBody @Valid ThingUpdateLocationCommand thingUpdatetLocationCommand) {
        return service.updateThingLocation(id, thingUpdatetLocationCommand);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Tárgy törlése ID alapján")
    @ApiResponse(
            responseCode = "204",
            description = "A tárgy törölése sikeres")
    @ApiResponse(
            responseCode = "404",
            description = "A tárgy nem található",
            content = @Content(mediaType = "application/problem+json"))
    public void deleteThingById(@PathVariable(name = "id") long id) {
        service.deleteThingById(id);
    }


    @PostMapping("/{id}/picture")
    @ResponseStatus(CREATED)
    @Operation(summary = "Kép hozzáadása tárgyhoz")
    @ApiResponse(
            responseCode = "201",
            description = "A kép a tárgyhoz hozzáadva")
    @ApiResponse(
            responseCode = "404",
            description = "A tárgy nem található",
            content = @Content(mediaType = "application/problem+json"))
    @ApiResponse(
            responseCode = "415",
            description = "A kép nem olvasható/hibás formátumú",
            content = @Content(mediaType = "application/problem+json"))
    public void addPictureToThing(
            @PathVariable long id,
            @Parameter(description = "csak jpg lehet") @RequestPart("file") @FileSize(MEDIUMBLOB) MultipartFile file
    ) {
        service.addPicture(id, file);
    }


    @GetMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Tárgy keresése ID alapján")
    @ApiResponse(
            responseCode = "200",
            description = "Tárgy a keresett ID-val",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(
            responseCode = "404",
            description = "A tárgy nem található",
            content = @Content(mediaType = "application/problem+json"))
    public ThingDto findThingById(@PathVariable(name = "id") long id) {
        return service.findById(id);
    }


}
