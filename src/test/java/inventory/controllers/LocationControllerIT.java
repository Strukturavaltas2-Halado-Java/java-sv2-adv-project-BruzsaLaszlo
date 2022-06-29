package inventory.controllers;

import inventory.model.dtos.LocationCreateCommand;
import inventory.model.dtos.LocationDto;
import inventory.model.dtos.LocationUpdateInfoCommand;
import inventory.model.entities.Location;
import inventory.repositories.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.zalando.problem.Problem;

import java.util.List;

import static inventory.model.enums.Room.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"DELETE FROM pictures", "DELETE FROM things", "DELETE FROM locations"})
class LocationControllerIT {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    LocationRepository locationRepository;

    Location nyaralo;

    @BeforeEach
    void inic() {
        nyaralo = locationRepository.save(new Location("Nyaraló", BASEMENT, "nedves"));
        locationRepository.save(new Location("own house", GARAGE));
        locationRepository.save(new Location("Mother house", GARAGE_ATTIC, "one stair broken!, full"));
        locationRepository.save(new Location("Mother house", HOUSE_ATTIC, "feljárnak a macskák"));
    }

    @Test
    void getAllLocation() {
        webTestClient
                .get()
                .uri("/api/locations")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(LocationDto.class)
                .hasSize(4);
    }

    @Test
    void testCreateLocation() {
        webTestClient
                .post()
                .uri("/api/locations")
                .bodyValue(new LocationCreateCommand("saját", HOUSE_ATTIC, "nincs létra"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("name").isEqualTo("saját")
                .jsonPath("room").isEqualTo("HOUSE_ATTIC")
                .jsonPath("info").isEqualTo("nincs létra");
    }

    @Test
    void testInvalidCreate() {
        webTestClient
                .post()
                .uri("/api/locations")
                .bodyValue(new LocationCreateCommand("", null, "nincs létra"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Problem.class);

        webTestClient
                .post()
                .uri("/api/locations")
                .bodyValue(new LocationCreateCommand(" ", BASEMENT, "nincs létra"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Problem.class);

        webTestClient
                .post()
                .uri("/api/locations")
                .bodyValue(new LocationCreateCommand(null, BASEMENT, "nincs létra"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Problem.class);
    }

    @Test
    void testDuplicateCreation() {
        webTestClient
                .post()
                .uri("/api/locations")
                .bodyValue(new LocationCreateCommand(nyaralo.getName(), nyaralo.getRoom(), nyaralo.getInfo()))
                .exchange()
                .expectStatus().is4xxClientError();

    }

    @Test
    void testUpdateLocationInfo() {
        String newInfo = "beletört a kulcs, nem zárható";
        webTestClient
                .put()
                .uri("/api/locations/{id}", nyaralo.getId())
                .bodyValue(new LocationUpdateInfoCommand(newInfo))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("info", newInfo);
    }

    @Test
    void testdeleteLocation() {
        webTestClient
                .delete()
                .uri("/api/locations/{id}", nyaralo.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testFindAllLocationNames() {
        webTestClient
                .get()
                .uri("/api/locations/names")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<String>>() {
                })
                .value(list -> assertThat(list).containsExactlyInAnyOrder("own house", "Nyaraló", "Mother house"));
    }

    @Test
    void testFindAllLocationRoomByName() {
        webTestClient
                .get()
                .uri("/api/locations/{name}/rooms", nyaralo.getName())
                .exchange()
                .expectBody(new ParameterizedTypeReference<List<String>>() {
                })
                .value(list -> assertThat(list).containsExactly("BASEMENT"));
    }

    @Test
    void testHandleNotFoundException() {
        webTestClient
                .delete()
                .uri("/api/locations/{id}", Long.MAX_VALUE)
                .exchange()
                .expectStatus().isNotFound();
    }
}