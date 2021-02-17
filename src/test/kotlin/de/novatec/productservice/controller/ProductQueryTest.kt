package src.test.kotlin.de.novatec.productservice.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import src.main.kotlin.de.novatec.productservice.ProductServiceApplication
import src.test.kotlin.de.novatec.productservice.*

@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = [ProductServiceApplication::class])
internal class ProductQueryTest {

    @Autowired
    private lateinit var testClient: WebTestClient

    @Test
    fun `should get product`() {
        val query = "getProduct"

        testClient.post()
            .uri(GRAPHQL_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(GRAPHQL_MEDIA_TYPE)
            .bodyValue("query { $query {name, id, description, price, category } }")
            .exchange()
            .verifyOnlyDataExists(query)
//            .jsonPath("$DATA_JSON_PATH.$query.orderDate").isEqualTo("9.2.2021")
//            .jsonPath("$DATA_JSON_PATH.$query.id").isEqualTo("602b936938e5ee596440a813")
//            .jsonPath("$DATA_JSON_PATH.$query.productIds").isEqualTo(arrayListOf("602b936938e5ee596440a811", "602b936938e5ee596440a812"))
//            .jsonPath("$DATA_JSON_PATH.$query.price").isEqualTo("610")
    }

    @Test
    fun `should get product by id`() {
        val query = "getProductById"

        testClient.post()
            .uri(GRAPHQL_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(GRAPHQL_MEDIA_TYPE)
            .bodyValue("query { $query(id: \"602b936938e5ee596440a811\") {name, id, description, price, category } }")
            .exchange()
            .verifyOnlyDataExists(query)
            .jsonPath("$DATA_JSON_PATH.$query.name").isEqualTo("Handy")
            .jsonPath("$DATA_JSON_PATH.$query.id").isEqualTo("602b936938e5ee596440a811")
            .jsonPath("$DATA_JSON_PATH.$query.description").isEqualTo("Bestes Handy")
            .jsonPath("$DATA_JSON_PATH.$query.price").isEqualTo("255")
            .jsonPath("$DATA_JSON_PATH.$query.category").isEqualTo("SMARTPHONE")
    }
}
