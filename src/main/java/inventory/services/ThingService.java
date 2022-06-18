package inventory.services;

import inventory.model.entities.Location;
import inventory.exceptions.LocationNotFoundException;
import inventory.repositories.LocationRepository;
import inventory.model.dtos.ThingCreateCommand;
import inventory.model.dtos.ThingDto;
import inventory.model.dtos.ThingUpdateDescriptionCommand;
import inventory.model.dtos.ThingUpdateLocationCommand;
import inventory.model.entities.Thing;
import inventory.exceptions.ThingNotFoundException;
import inventory.repositories.ThingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ThingService {

    private final ThingRepository thingRepository;
    private final LocationRepository locationRepository;

    private final ModelMapper modelMapper;

    public List<ThingDto> findAllThing() {
        List<Thing> all = thingRepository.findAll();
        return modelMapper.map(all, new TypeToken<List<ThingDto>>() {
        }.getType());
    }

    //    @Transactional
    public ThingDto createThing(ThingCreateCommand thingCreateCommand) {
        Location location = locationRepository.findById(thingCreateCommand.getLocationId())
                .orElseThrow(() -> new LocationNotFoundException(thingCreateCommand.getLocationId()));
        Thing thing = new Thing(location, thingCreateCommand.getType(), thingCreateCommand.getDescription());
        thingRepository.save(thing);
        return modelMapper.map(thing, ThingDto.class);
    }

    @Transactional
    public ThingDto updateThingDescription(long id, ThingUpdateDescriptionCommand thingUpdateDescriptionCommand) {
        Thing thing = findById(id);
        thing.setDescription(thingUpdateDescriptionCommand.getDescription());
        return modelMapper.map(thing, ThingDto.class);
    }

    @Transactional
    public ThingDto updateThingLocation(long id, ThingUpdateLocationCommand thingUpdateLocationCommand) {
        Location location = locationRepository.findById(thingUpdateLocationCommand.getLocationId())
                .orElseThrow(() -> new LocationNotFoundException(thingUpdateLocationCommand.getLocationId()));
        Thing thing = findById(id);
        thing.setLocation(location);
        return modelMapper.map(thing, ThingDto.class);
    }

    private Thing findById(long id) {
        return thingRepository.findById(id).orElseThrow(() -> new ThingNotFoundException(id));
    }

    public void deleteThingById(long id) {
        try {
            thingRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ThingNotFoundException(id);
        }
    }
}
