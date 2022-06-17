package inventory.services;

import inventory.exceptions.LocationNotFoundException;
import inventory.model.dtos.LocationCreateCommand;
import inventory.model.dtos.LocationDto;
import inventory.model.dtos.LocationInfoCommand;
import inventory.model.entities.Location;
import inventory.repositories.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    private final ModelMapper modelMapper;


    public List<LocationDto> findAllLocation() {
        List<Location> result = locationRepository.findAll();
        return modelMapper.map(result, new TypeToken<List<LocationDto>>() {
        }.getType());
    }

    public LocationDto createLocation(LocationCreateCommand locationCreateCommand) {
        Location location = modelMapper.map(locationCreateCommand, Location.class);
        locationRepository.save(location);
        return modelMapper.map(location, LocationDto.class);
    }

    @Transactional
    public LocationDto updateLocationInfo(long id, LocationInfoCommand locationInfoCommand) {
        Location location = locationRepository.findById(id).orElseThrow(() -> new LocationNotFoundException(id));
        location.setInfo(locationInfoCommand.getInfo());
        return modelMapper.map(location, LocationDto.class);
    }

    public void deleteLocation(long id) {
        try {
            locationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new LocationNotFoundException(id);
        }
    }
}
