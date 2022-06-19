package inventory.services;

import inventory.exceptions.LocationCreateException;
import inventory.exceptions.LocationNotFoundException;
import inventory.model.dtos.LocationCreateCommand;
import inventory.model.dtos.LocationDto;
import inventory.model.dtos.LocationUpdateInfoCommand;
import inventory.model.entities.Location;
import inventory.model.enums.Room;
import inventory.repositories.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository repository;

    private final ModelMapper modelMapper;


    public List<LocationDto> findAllLocation() {
        List<Location> result = repository.findAll();
        return modelMapper.map(result, new TypeToken<List<LocationDto>>() {
        }.getType());
    }

    public LocationDto createLocation(LocationCreateCommand locationCreateCommand) {
        if (repository.exists(Example.of(new Location(locationCreateCommand.getName(), locationCreateCommand.getRoom())))) {
            throw new LocationCreateException(locationCreateCommand.getName(), locationCreateCommand.getRoom());
        }

        Location location = modelMapper.map(locationCreateCommand, Location.class);
        repository.save(location);
        return modelMapper.map(location, LocationDto.class);
    }

    @Transactional
    public LocationDto updateLocationInfo(long id, LocationUpdateInfoCommand locationUpdateInfoCommand) {
        Location location = repository.findById(id).orElseThrow(() -> new LocationNotFoundException(id));
        location.setInfo(locationUpdateInfoCommand.getInfo());
        return modelMapper.map(location, LocationDto.class);
    }

    public void deleteLocation(long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new LocationNotFoundException(id);
        }
    }

    public List<String> getAllLocationNames() {
        return repository.findAllNames();
    }

    public List<Room> findAllLocationRoomByName(String name) {
        return repository.findAllRooms(name);
    }

}
