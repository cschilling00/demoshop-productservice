package src.test.kotlin.de.novatec.productservice.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import src.main.kotlin.de.novatec.productservice.ProductServiceApplication

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [ProductServiceApplication::class]

)
class OrderControllerTest(@Autowired val client: TestRestTemplate){

    @Test
    fun `should get all products`(){
        val entity = client.getForEntity("/products",String::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.hasBody())
    }

    @Test
    fun `should get product by id`(){
        val entity = client.getForEntity("/products/602b936938e5ee596440a811",String::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.hasBody())
    }
}