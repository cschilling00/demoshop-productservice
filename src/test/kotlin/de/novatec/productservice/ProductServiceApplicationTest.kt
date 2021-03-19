package src.test.kotlin.de.novatec.productservice

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import src.main.kotlin.de.novatec.productservice.ProductServiceApplication

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProductServiceApplication)
internal class ProductServiceApplicationTest {
    @Test
    fun contextLoads() {
    }
}