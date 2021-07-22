package src.test.kotlin.de.novatec.productservice

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import src.main.kotlin.de.novatec.productservice.ProductServiceApplication

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [ProductServiceApplication::class])
class ProductServiceApplicationTests {

	@Test
	fun contextLoads() {
	}

}
