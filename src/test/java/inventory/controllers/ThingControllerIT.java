package inventory.controllers;

import inventory.model.dtos.ThingCreateCommand;
import inventory.model.dtos.ThingDto;
import inventory.model.dtos.ThingUpdateDescriptionCommand;
import inventory.model.dtos.ThingUpdateLocationCommand;
import inventory.model.entities.Location;
import inventory.model.entities.Thing;
import inventory.model.enums.Room;
import inventory.repositories.LocationRepository;
import inventory.repositories.ThingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.zalando.problem.Problem;

import static inventory.model.enums.Room.BASEMENT;
import static inventory.model.enums.ThingType.*;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"DELETE FROM pictures", "DELETE FROM things", "DELETE FROM locations"})
class ThingControllerIT {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    ThingRepository thingRepository;

    Thing powerSupply;
    Location location;

    @BeforeEach
    void inic() {
        location = locationRepository.save(new Location("Nyaraló", BASEMENT));

        powerSupply = thingRepository.save(new Thing(location, ELECTRIC, "power supply 12V"));
        thingRepository.save(new Thing(location, HAND_TOOL, "1/2\" racsnis bejható + dugókulcs készlet"));
        thingRepository.save(new Thing(location, HAND_TOOL, "28mm dugókulcs"));
        thingRepository.save(new Thing(location, FISHING, "kisháló 1x1m"));
        thingRepository.save(new Thing(location, FISHING, "pontyzsák"));
        thingRepository.save(new Thing(location, FISHING, "leszúrható bottartó"));
        thingRepository.save(new Thing(location, GARDEN_TOOL, "metszőolló"));
    }

    @Test
    void testFindAllThing() {
        webTestClient
                .get()
                .uri("/api/things")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ThingDto.class)
                .hasSize(7)
                .value(thingDtos -> assertThat(thingDtos)
                        .extracting(ThingDto::getType)
                        .contains(ELECTRIC, GARDEN_TOOL, HAND_TOOL, FISHING));
    }

    @Test
    void testCreateThing() {
        webTestClient
                .post()
                .uri("/api/things")
                .bodyValue(new ThingCreateCommand(location.getId(), ELECTRIC, "230V -> 12V 2A"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ThingDto.class)
                .value(thingDto -> thingDto.getLocation().getId(), equalTo(location.getId()))
                .value(thingDto -> assertThat(thingDto.getUpdated()).isCloseTo(now(), within(1, SECONDS)))
                .value(thingDto -> assertThat(thingDto.getDescription()).contains("12V"));
    }

    @Test
    void testInvalidCreateThing() {
        webTestClient
                .post()
                .uri("/api/things")
                .bodyValue(new ThingCreateCommand(null, ELECTRIC, "230V -> 12V 2A"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Problem.class);

        webTestClient
                .post()
                .uri("/api/things")
                .bodyValue(new ThingCreateCommand(location.getId(), null, "230V -> 12V 2A"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Problem.class);

        webTestClient
                .post()
                .uri("/api/things")
                .bodyValue(new ThingCreateCommand(location.getId(), ELECTRIC, null))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Problem.class);

        webTestClient
                .post()
                .uri("/api/things")
                .bodyValue(new ThingCreateCommand(location.getId(), ELECTRIC, "   "))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Problem.class);
    }

    @Test
    void testUpdateThingDescription() {
        String description = "power supply 12V/2A, DC-Jack diameter 5.5mm/2.1mm";
        webTestClient
                .put()
                .uri("/api/things/{id}/description", powerSupply.getId())
                .bodyValue(new ThingUpdateDescriptionCommand(description))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(ThingDto.class)
                .value(ThingDto::getDescription, equalTo(description));
    }

    @Test
    void testInvalidUpdateThingDescription() {
        webTestClient
                .put()
                .uri("/api/things/{id}/description", powerSupply.getId())
                .bodyValue(new ThingUpdateDescriptionCommand())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Problem.class);
    }

    @Test
    void testUpdateThingLocation() {
        Location newLocation = locationRepository.save(new Location("saját", Room.GARAGE_ATTIC));
        webTestClient
                .put()
                .uri("/api/things/{id}/location", powerSupply.getId())
                .bodyValue(new ThingUpdateLocationCommand(newLocation.getId()))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.location.name").isEqualTo("saját");
    }

    @Test
    void testInvalidUpdateThingLocation() {
        Location newLocation = locationRepository.save(new Location("saját", Room.GARAGE_ATTIC));
        webTestClient
                .put()
                .uri("/api/things/{id}/location", powerSupply.getId())
                .bodyValue(new ThingUpdateLocationCommand())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Problem.class);
    }

    @Test
    void testDeleteThingById() {
        webTestClient
                .delete()
                .uri("/api/things/{id}", powerSupply.getId())
                .exchange()
                .expectStatus().isNoContent();

        webTestClient
                .get()
                .uri("/api/things")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(ThingDto.class)
                .hasSize(6)
                .value(thingDtos -> assertThat(thingDtos)
                        .extracting(ThingDto::getId)
                        .isNotEmpty()
                        .doesNotContain(powerSupply.getId()));
    }

    @Test
    void testGetFilteredThings() {
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/things")
                        .queryParam("description", "dugókulcs")
                        .build())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(ThingDto.class)
                .hasSize(2)
                .value(thingDtos -> assertThat(thingDtos)
                        .extracting(ThingDto::getType)
                        .containsExactly(HAND_TOOL, HAND_TOOL));
    }

    @Test
    void testPictureUpload() {
        var builder = new MultipartBodyBuilder();
        builder.part("file", new ClassPathResource("/pic/IMG_20220629_095936.jpg"));

        webTestClient
                .post()
                .uri("/api/things/{id}/picture", powerSupply.getId())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isCreated();

        webTestClient
                .get()
                .uri("/api/things/{id}", powerSupply.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ThingDto.class)
                .value(thingDto -> assertThat(thingDto.getPictures()).hasSize(1));
    }

    @Test
    void testPictureUploadWithInvalidFile() {
        var builder = new MultipartBodyBuilder();
        builder.part("file", new ClassPathResource("thing.http"));

        webTestClient
                .post()
                .uri("/api/things/{id}/picture", powerSupply.getId())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Problem.class);
    }

    @Test
    void testHandleNotFoundException() {
        webTestClient
                .delete()
                .uri("/api/things/{id}", Long.MAX_VALUE)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void testLastMoved() {
        webTestClient
                .get()
                .uri("/api/things/last-moved")
                .exchange()
                .expectBodyList(ThingDto.class)
                .hasSize(7);

        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/things/last-moved")
                        .queryParam("count", 3)
                        .build())
                .exchange()
                .expectBodyList(ThingDto.class)
                .hasSize(3);

        Location newLocation = locationRepository.save(new Location("saját", Room.GARAGE_ATTIC));
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/things/last-moved")
                        .queryParam("location-id", newLocation.getId())
                        .build())
                .exchange()
                .expectBodyList(ThingDto.class)
                .hasSize(0);
    }
}