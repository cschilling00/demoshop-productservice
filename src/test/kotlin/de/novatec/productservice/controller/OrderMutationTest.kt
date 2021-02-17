package src.test.kotlin.de.novatec.productservice.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import src.main.kotlin.de.novatec.productservice.ProductServiceApplication
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import src.test.kotlin.de.novatec.productservice.*
import src.test.kotlin.de.novatec.productservice.verifyOnlyDataExists

@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ContextConfiguration(classes = [ProductServiceApplication::class])
@SpringBootTest(classes = [ProductServiceApplication::class])
internal class OrderMutationTest() {

    @Autowired

    private lateinit var testClient: WebTestClient

    @Test
    fun `verify OrderMutation editOrder`() {

        val query = "editOrder"

        testClient.post()
                .uri(GRAPHQL_ENDPOINT)
                .accept(APPLICATION_JSON)
                .contentType(GRAPHQL_MEDIA_TYPE)
                .bodyValue("mutation { $query(order: {id: \"602b936938e5ee596440a813\", orderDate: \"9.2.2021\", price: 710, productIds: [\"602b936938e5ee596440a811\",\"602b936938e5ee596440a812\"]}) { orderDate, id, productIds, price } }")
                .exchange()
                .verifyOnlyDataExists(query)
                .jsonPath("$DATA_JSON_PATH.$query.orderDate").isEqualTo("9.2.2021")
                .jsonPath("$DATA_JSON_PATH.$query.id").isEqualTo("602b936938e5ee596440a813")
                .jsonPath("$DATA_JSON_PATH.$query.productIds").isEqualTo(arrayListOf("602b936938e5ee596440a811","602b936938e5ee596440a812"))
                .jsonPath("$DATA_JSON_PATH.$query.price").isEqualTo("710")

    }

    @Test
    fun `verify OrderMutation createOrder`() {
        val query = "createOrder"

        testClient.post()
                .uri(GRAPHQL_ENDPOINT)
                .accept(APPLICATION_JSON)
                .contentType(GRAPHQL_MEDIA_TYPE)
                .bodyValue("mutation { $query(order: {id: \"602b936938e5ee596440a814\", orderDate: \"10.2.2021\", productIds: [\"602b936938e5ee596440a811\",\"602b936938e5ee596440a811\"], price: 555}) {orderDate, id, productIds, price } }")
                .exchange()
                .verifyOnlyDataExists(query)
                .jsonPath("$DATA_JSON_PATH.$query.orderDate").isEqualTo("10.2.2021")
                .jsonPath("$DATA_JSON_PATH.$query.id").isEqualTo("602b936938e5ee596440a814")
                .jsonPath("$DATA_JSON_PATH.$query.productIds").isEqualTo(arrayListOf("602b936938e5ee596440a811","602b936938e5ee596440a811"))
                .jsonPath("$DATA_JSON_PATH.$query.price").isEqualTo("555")
    }

    @Test
    fun `verify OrderMutation deleteOrder`() {

        val query = "deleteOrder"
        val data = "Order deleted: Order(id=602b936938e5ee596440a813, productIds=[602b936938e5ee596440a811, 602b936938e5ee596440a812], orderDate=9.2.2021, price=610)"

        testClient.post()
                .uri(GRAPHQL_ENDPOINT)
                .accept(APPLICATION_JSON)
                .contentType(GRAPHQL_MEDIA_TYPE)
                .bodyValue("mutation { $query(orderId: \"602b936938e5ee596440a813\")}")
                .exchange()
                .verifyData(query, data)
    }
}