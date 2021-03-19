package src.test.kotlin.de.novatec.productservice.controller

import com.graphql.spring.boot.test.GraphQLTest
import com.graphql.spring.boot.test.GraphQLTestTemplate
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration

import src.main.kotlin.de.novatec.productservice.ProductServiceApplication

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [ProductServiceApplication.class])
internal class OrderQueryTest(var graphQLTestTemplate: GraphQLTestTemplate) {


    @Test
    fun `Orders are returned`() {
        graphQLTestTemplate
                .postForResource("graphql/create-user.graphql")
                .also { println(it) }
    }

    @Test
    fun getOrderById() {
    }

    @Test
    fun getOrderByUserId() {
    }
}