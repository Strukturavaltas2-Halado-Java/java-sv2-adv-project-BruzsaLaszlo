package inventory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testInvalidEndpoint() {
        webTestClient
                .get()
                .uri("/invalid-url")
                .exchange()
                .expectStatus().isNotFound();
    }

}
