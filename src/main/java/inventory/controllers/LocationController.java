package inventory.controllers;

import inventory.model.dtos.LocationCreateCommand;
import inventory.model.dtos.LocationDto;
import inventory.model.dtos.LocationUpdateInfoCommand;
import inventory.model.enums.Room;
import inventory.services.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
public class LocationController {


    private final LocationService service;


    @GetMapping
    @Operation(summary = "Get all locations")
    @ApiResponse(
            responseCode = "200",
            description = "All locations",
            content = @Content(mediaType = "application/json"))
    public List<LocationDto> getAllLocation() {
        return service.findAllLocation();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a location")
    @ApiResponse(
            responseCode = "201",
            description = "Location has been created",
            content = @Content(mediaType = "application/json"))
    public LocationDto createLocation(@RequestBody @Valid LocationCreateCommand locationCreateCommand) {
        System.out.println(locationCreateCommand);
        return service.createLocation(locationCreateCommand);
    }


    @PutMapping("/{id}")
    @ResponseStatus(ACCEPTED)
    @Operation(summary = "Update location description by id")
    @ApiResponse(
            responseCode = "202",
            description = "Location description has been updated",
            content = @Content(mediaType = "application/json"))
    public LocationDto updateLocationInfo(
            @PathVariable(name = "id") long id,
            @RequestBody LocationUpdateInfoCommand locationUpdateInfoCommand) {
        return service.updateLocationInfo(id, locationUpdateInfoCommand);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete location by id")
    @ApiResponse(
            responseCode = "204",
            description = "Location has been deleted")
    public void deleteLocation(@PathVariable(name = "id") long id) {
        service.deleteLocation(id);
    }


    @GetMapping("/names")
    @Operation(summary = "Get all location names")
    @ApiResponse(
            responseCode = "200",
            description = "All location names",
            content = @Content(mediaType = "application/json"))
    public List<String> findAllLocationNames() {
        return service.getAllLocationNames();
    }


    @GetMapping("/{name}/rooms")
    @Operation(summary = "Get all location names")
    @ApiResponse(
            responseCode = "200",
            description = "All location names",
            content = @Content(mediaType = "application/json"))
    public List<Room> findAllLocationRoomByName(@PathVariable(name = "name") String name) {
        return service.findAllLocationRoomByName(name);
    }

}
