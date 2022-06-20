package inventory.controllers;

import inventory.model.dtos.LocationCreateCommand;
import inventory.model.dtos.LocationDto;
import inventory.model.dtos.LocationUpdateInfoCommand;
import inventory.model.enums.Room;
import inventory.services.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@Tag(name = "`Location` végpontok")
public class LocationController {


    private final LocationService service;


    @GetMapping
    @Operation(summary = "Az összes hely lekérdezése")
    @ApiResponse(
            responseCode = "200",
            description = "Az összes hely egy listában",
            content = @Content(mediaType = "application/json"))
    public List<LocationDto> getAllLocation() {
        return service.findAllLocation();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Új hely mentése")
    @ApiResponse(
            responseCode = "201",
            description = "Új hely elmentve",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(
            responseCode = "406",
            description = "A hely már létezik",
            content = @Content(mediaType = "application/problem+json"))
    public LocationDto createLocation(@RequestBody @Valid LocationCreateCommand locationCreateCommand) {
        return service.createLocation(locationCreateCommand);
    }


    @PutMapping("/{id}")
    @ResponseStatus(ACCEPTED)
    @Operation(summary = "A hely információ frissítése")
    @ApiResponse(
            responseCode = "202",
            description = "A hely információ frissítése sikeres",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(
            responseCode = "404",
            description = "A hely nem található",
            content = @Content(mediaType = "application/problem+json"))
    public LocationDto updateLocationInfo(
            @PathVariable(name = "id") long id,
            @RequestBody LocationUpdateInfoCommand locationUpdateInfoCommand) {
        return service.updateLocationInfo(id, locationUpdateInfoCommand);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Hely törlése ID alapján")
    @ApiResponse(
            responseCode = "204",
            description = "A hely eltávolításra került")
    @ApiResponse(
            responseCode = "404",
            description = "A hely nem található",
            content = @Content(mediaType = "application/problem+json"))
    public void deleteLocation(@PathVariable(name = "id") long id) {
        service.deleteLocation(id);
    }


    @GetMapping("/names")
    @Operation(summary = "Az összes hely nevének listája")
    @ApiResponse(
            responseCode = "200",
            description = "A helyek neveinek listája",
            content = @Content(mediaType = "application/json"))
    public List<String> findAllLocationNames() {
        return service.getAllLocationNames();
    }


    @GetMapping("/{name}/rooms")
    @Operation(summary = "A hely összes helyisége")
    @ApiResponse(
            responseCode = "200",
            description = "A helyiségek listája",
            content = @Content(mediaType = "application/json"))
    public List<Room> findAllLocationRoomByName(@PathVariable(name = "name") String name) {
        return service.findAllLocationRoomByName(name);
    }

}
