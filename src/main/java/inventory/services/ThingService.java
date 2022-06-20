package inventory.services;

import inventory.exceptions.LocationNotFoundException;
import inventory.exceptions.PictureUploadException;
import inventory.exceptions.ThingNotFoundException;
import inventory.model.dtos.ThingCreateCommand;
import inventory.model.dtos.ThingDto;
import inventory.model.dtos.ThingUpdateDescriptionCommand;
import inventory.model.dtos.ThingUpdateLocationCommand;
import inventory.model.entities.Location;
import inventory.model.entities.Thing;
import inventory.model.enums.ThingType;
import inventory.repositories.LocationRepository;
import inventory.repositories.ThingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThingService {

    private final ThingRepository thingRepository;
    private final LocationRepository locationRepository;

    private final ModelMapper modelMapper;

    public List<ThingDto> findThingsByDescriptionAndType(Optional<String> description, Optional<ThingType> type) {
        List<Thing> result = thingRepository.findThingsByDescriptionAndType(description, type);
        return modelMapper.map(result, new TypeToken<List<ThingDto>>() {
        }.getType());
    }

    public ThingDto createThing(ThingCreateCommand thingCreateCommand) {
        Location location = findLocationById(thingCreateCommand.getLocationId());
        Thing thing = new Thing(location, thingCreateCommand.getType(), thingCreateCommand.getDescription());
        thingRepository.save(thing);
        return modelMapper.map(thing, ThingDto.class);
    }

    @Transactional
    public ThingDto updateThingDescription(long id, ThingUpdateDescriptionCommand thingUpdateDescriptionCommand) {
        Thing thing = findThingById(id);
        thing.setDescription(thingUpdateDescriptionCommand.getDescription());
        return modelMapper.map(thing, ThingDto.class);
    }

    @Transactional
    public ThingDto updateThingLocation(long id, ThingUpdateLocationCommand thingUpdateLocationCommand) {
        Location location = findLocationById(thingUpdateLocationCommand.getLocationId());
        Thing thing = findThingById(id);
        thing.setLocation(location);
        return modelMapper.map(thing, ThingDto.class);
    }

    public void deleteThingById(long id) {
        try {
            thingRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ThingNotFoundException(id);
        }
    }

    @Transactional
    public void addPicture(long id, MultipartFile picture) {
        try {
            Thing thing = thingRepository.getReferenceById(id);
            thing.addPicture(picture.getContentType(), picture.getBytes());
        } catch (IOException e) {
            throw new PictureUploadException(e.getMessage());
        }
    }

    public ThingDto findById(long id) {
        return modelMapper.map(findThingById(id), ThingDto.class);
    }

    private Thing findThingById(long id) {
        return thingRepository.findById(id).orElseThrow(() -> new ThingNotFoundException(id));
    }

    private Location findLocationById(long id) {
        return locationRepository.findById(id).orElseThrow(() -> new LocationNotFoundException(id));
    }

}
