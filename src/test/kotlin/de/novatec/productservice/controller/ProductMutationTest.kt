package src.test.kotlin.de.novatec.productservice.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient
import src.main.kotlin.de.novatec.productservice.ProductServiceApplication
import src.test.kotlin.de.novatec.productservice.DATA_JSON_PATH
import src.test.kotlin.de.novatec.productservice.GRAPHQL_ENDPOINT
import src.test.kotlin.de.novatec.productservice.GRAPHQL_MEDIA_TYPE
import src.test.kotlin.de.novatec.productservice.verifyData
import src.test.kotlin.de.novatec.productservice.verifyOnlyDataExists

@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = [ProductServiceApplication::class])
internal class ProductMutationTest() {

    @Autowired
    private lateinit var testClient: WebTestClient

    @Test
    fun `should edit product`() {

        val query = "editProduct"

        testClient.post()
            .uri(GRAPHQL_ENDPOINT)
            .accept(APPLICATION_JSON)
            .contentType(GRAPHQL_MEDIA_TYPE)
            .bodyValue("mutation { $query(product: {id: \"602b936938e5ee596440a812\", name: \"mp3 Player\", description: \"lala lala\", price: 155, category: MP3}) { name, id, description, price, category } }")
            .exchange()
            .verifyOnlyDataExists(query)
            .jsonPath("$DATA_JSON_PATH.$query.name").isEqualTo("mp3 Player")
            .jsonPath("$DATA_JSON_PATH.$query.id").isEqualTo("602b936938e5ee596440a812")
            .jsonPath("$DATA_JSON_PATH.$query.description").isEqualTo("lala lala")
            .jsonPath("$DATA_JSON_PATH.$query.price").isEqualTo("155")
            .jsonPath("$DATA_JSON_PATH.$query.category").isEqualTo("MP3")
    }

    @Test
    fun `should create product`() {
        val query = "createProduct"

        testClient.post()
            .uri(GRAPHQL_ENDPOINT)
            .accept(APPLICATION_JSON)
            .contentType(GRAPHQL_MEDIA_TYPE)
            .bodyValue("mutation { $query(product: {id: \"602b936938e5ee596440a818\", name: \"mp3 Player\", description: \"lala lala\", price: 155, category:MP3}) {name, id, description, price, category } }")
            .exchange()
            .verifyOnlyDataExists(query)
            .jsonPath("$DATA_JSON_PATH.$query.id").isEqualTo("602b936938e5ee596440a818")
            .jsonPath("$DATA_JSON_PATH.$query.name").isEqualTo("mp3 Player")
            .jsonPath("$DATA_JSON_PATH.$query.description").isEqualTo("lala lala")
            .jsonPath("$DATA_JSON_PATH.$query.price").isEqualTo("155")
            .jsonPath("$DATA_JSON_PATH.$query.category").isEqualTo("MP3")
    }

    @Test
    fun `should delete product`() {

        val query = "deleteProduct"
        val data = "Product successfully deleted"

        testClient.post()
            .uri(GRAPHQL_ENDPOINT)
            .accept(APPLICATION_JSON)
            .contentType(GRAPHQL_MEDIA_TYPE)
            .bodyValue("mutation { $query(productId: \"602b936938e5ee596440a811\")}")
            .exchange()
            .verifyData(query, data)
    }
}
