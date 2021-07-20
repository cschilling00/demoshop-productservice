package src.test.kotlin.de.novatec.productservice

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import src.main.kotlin.de.novatec.productservice.ProductServiceApplication

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [ProductServiceApplication::class])
class ProductServiceApplicationTests {

	@Test
	fun contextLoads() {
	}

}
