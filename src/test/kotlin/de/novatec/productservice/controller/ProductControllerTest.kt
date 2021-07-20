package src.test.kotlin.de.novatec.productservice.controller

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.containing
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity.status
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import src.main.kotlin.de.novatec.productservice.ProductServiceApplication
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import src.main.kotlin.de.novatec.productservice.model.Category
import src.main.kotlin.de.novatec.productservice.model.Order
import src.main.kotlin.de.novatec.productservice.model.Product
import src.main.kotlin.de.novatec.productservice.repository.OrderRepository
import src.main.kotlin.de.novatec.productservice.repository.ProductRepository
import src.test.kotlin.de.novatec.productservice.WireMockInitializer

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@ContextConfiguration(initializers =  [WireMockInitializer::class])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [ProductServiceApplication::class])
class ProductControllerTest(@Autowired val mockMvc: MockMvc,
                            @Autowired val productRepository: ProductRepository,
                            @Autowired val orderRepository: OrderRepository,
                            @Autowired val wireMockServer: WireMockServer){

    val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaXNzIjoicHJvZHVjdHNlcnZpY2UtYXBpIiwiaWQiOiI2MDJhNzQxNjRmOWZmNjQwOGFhZDVkYTYiLCJleHAiOjE2MjQ1NTY2MDYsImlhdCI6MTYyNDU0MjIwNn0.wVK4ORU19UDmuAjZukPqIyk4jRnelCygRORNk-zLsAm99G9nItKlnAYOhRmed8ovQuhQ4voWCM_5HxJtG4b7bA"

    @BeforeEach
    fun callUsermanagement(){
        wireMockServer.stubFor(
            WireMock.get("/users/authorities")
                .withHeader("Authorization", containing("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaXNzIjoicHJvZHVjdHNlcnZpY2UtYXBpIiwiaWQiOiI2MDJhNzQxNjRmOWZmNjQwOGFhZDVkYTYiLCJleHAiOjE2MjQ1NTY2MDYsImlhdCI6MTYyNDU0MjIwNn0.wVK4ORU19UDmuAjZukPqIyk4jRnelCygRORNk-zLsAm99G9nItKlnAYOhRmed8ovQuhQ4voWCM_5HxJtG4b7bA"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody("[ROLE_ANONYMOUS]")
                )
        );
    }

    @BeforeAll
    fun loadRepository(){
        productRepository.deleteAll()
        productRepository.save(Product("602b936938e5ee596440a811", "Handy", "Bestes Handy", 255f, Category.SMARTPHONE))
        productRepository.save(Product("602b936938e5ee596440a812", "iPod", "Bester iPod", 355f, Category.MP3))

        orderRepository.deleteAll()
        orderRepository.save(Order("602b936938e5ee596440a813", listOf(Product("602b936938e5ee596440a811", "Handy", "Bestes Handy", 255f, Category.SMARTPHONE), Product("602b936938e5ee596440a812", "iPod", "Bester iPod", 355f, Category.MP3)), "9.2.2021", 610f, "602a74164f9ff6408aad5da6"))
        orderRepository.save(Order("602b936938e5ee596440a814", listOf(Product("602b936938e5ee596440a812", "iPod", "Bester iPod", 355f, Category.MP3)), "8.2.2021", 810f, "602a74164f9ff6408aad5da7"))
    }

    @AfterEach
    fun afterEach() {
        wireMockServer.resetAll()
    }

    @Test
    fun `should get all products`(){

        val expectedResponse = "[{\"id\":\"602b936938e5ee596440a811\",\"name\":\"Handy\",\"description\":\"Bestes Handy\",\"price\":255.0,\"category\":\"SMARTPHONE\"},{\"id\":\"602b936938e5ee596440a812\",\"name\":\"iPod\",\"description\":\"Bester iPod\",\"price\":355.0,\"category\":\"MP3\"}]"

        val result = mockMvc.perform(get("/products").header("Authorization", token))
            .andExpect { status(HttpStatus.OK) }
            .andReturn()

        assertEquals(expectedResponse, result.response.contentAsString)
    }

    @Test
    fun `should get product by id`(){

        val expectedResponse = "{\"id\":\"602b936938e5ee596440a811\",\"name\":\"Handy\",\"description\":\"Bestes Handy\",\"price\":255.0,\"category\":\"SMARTPHONE\"}"

        val result = mockMvc.perform(get("/products/602b936938e5ee596440a811").header("Authorization", token))
            .andExpect { status(HttpStatus.OK) }
            .andReturn()

        assertEquals(expectedResponse, result.response.contentAsString)
    }
}