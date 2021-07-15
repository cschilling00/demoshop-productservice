package src.test.kotlin.de.novatec.productservice.controller

import com.graphql.spring.boot.test.GraphQLTestTemplate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import src.main.kotlin.de.novatec.productservice.ProductServiceApplication
import src.main.kotlin.de.novatec.productservice.model.Category
import src.main.kotlin.de.novatec.productservice.model.Order
import src.main.kotlin.de.novatec.productservice.model.Product
import src.main.kotlin.de.novatec.productservice.repository.OrderRepository
import src.main.kotlin.de.novatec.productservice.repository.ProductRepository
import java.io.File
import java.nio.charset.Charset

@ExtendWith(
    SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [ProductServiceApplication::class])
internal class ProductMutationTest(@Autowired val graphQLTestTemplate: GraphQLTestTemplate,
                                   @Autowired val productRepository: ProductRepository,
                                   @Autowired val orderRepository: OrderRepository
) {

    @BeforeAll
    fun setUp() {
        graphQLTestTemplate.addHeader(
            "Authorization",
            "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaXNzIjoicHJvZHVjdHNlcnZpY2UtYXBpIiwiaWQiOiI2MDJhNzQxNjRmOWZmNjQwOGFhZDVkYTYiLCJleHAiOjE2MjQ1NTY2MDYsImlhdCI6MTYyNDU0MjIwNn0.wVK4ORU19UDmuAjZukPqIyk4jRnelCygRORNk-zLsAm99G9nItKlnAYOhRmed8ovQuhQ4voWCM_5HxJtG4b7bA"
        )
    }

    @BeforeAll
    fun loadRepository(){
        productRepository.deleteAll()
        productRepository.save(Product("602b936938e5ee596440a811", "Handy", "Device to telephone and do other things", 255f, Category.SMARTPHONE))
        productRepository.save(Product("602b936938e5ee596440a812", "iPod", "Device to listen to music or play games", 355f, Category.MP3))

        orderRepository.deleteAll()
        orderRepository.save(Order("602b936938e5ee596440a813", listOf(Product("602b936938e5ee596440a811", "Handy", "Bestes Handy", 255f, Category.SMARTPHONE), Product("602b936938e5ee596440a812", "iPod", "Bester iPod", 355f, Category.MP3)), "9.2.2021", 610f, "602a74164f9ff6408aad5da6"))
        orderRepository.save(Order("602b936938e5ee596440a814", listOf(Product("602b936938e5ee596440a812", "iPod", "Bester iPod", 355f, Category.MP3)), "8.2.2021", 810f, "602a74164f9ff6408aad5da7"))
    }

    @Test
    fun `should create a new product`(){
        var response = graphQLTestTemplate.postForResource("request/products.graphql")
//            println("response: "+response.rawResponse.body.toString())
        val expectedResponse = File("src/test/resources/response/productsRes.json").readText(Charset.defaultCharset())
        Assertions.assertNotNull(response)
        Assertions.assertTrue(response.isOk)
        JSONAssert.assertEquals(expectedResponse, response.rawResponse.body, true)
    }




}
