package src.test.kotlin.de.novatec.productservice.controller

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.containing
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
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
class OrderControllerTest(@Autowired val mockMvc: MockMvc,
                          @Autowired val productRepository: ProductRepository,
                          @Autowired val orderRepository: OrderRepository,
                          @Autowired val wireMockServer: WireMockServer){

    val token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaXNzIjoicHJvZHVjdHNlcnZpY2UtYXBpIiwiaWQiOiI2MDJhNzQxNjRmOWZmNjQwOGFhZDVkYTYiLCJpYXQiOjE2MjU2ODAzNjN9.TIm2oPpBxcMqVh8lfV_0aj-bcLgK84jn2HJ0wpchZRzWyu-DkozJr5QkPMwCPPBnnYjUQIM1C5c0WjBKgCEgAQ"

    @BeforeEach
    fun callUsermanagement(){
        wireMockServer.stubFor(
            WireMock.get("/users/authorities")
                .withHeader("Authorization", containing("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaXNzIjoicHJvZHVjdHNlcnZpY2UtYXBpIiwiaWQiOiI2MDJhNzQxNjRmOWZmNjQwOGFhZDVkYTYiLCJpYXQiOjE2MjU2ODAzNjN9.TIm2oPpBxcMqVh8lfV_0aj-bcLgK84jn2HJ0wpchZRzWyu-DkozJr5QkPMwCPPBnnYjUQIM1C5c0WjBKgCEgAQ"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody("user")
                )
        );
    }

    @BeforeEach
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
    fun `should get order by userId`(){

        val expectedResponse = "[{\"id\":\"602b936938e5ee596440a813\",\"products\":[{\"id\":\"602b936938e5ee596440a811\",\"name\":\"Handy\",\"description\":\"Bestes Handy\",\"price\":255.0,\"category\":\"SMARTPHONE\"},{\"id\":\"602b936938e5ee596440a812\",\"name\":\"iPod\",\"description\":\"Bester iPod\",\"price\":355.0,\"category\":\"MP3\"}],\"orderDate\":\"9.2.2021\",\"price\":610.0,\"userId\":\"602a74164f9ff6408aad5da6\"}]"

        val result = mockMvc.perform(get("/orders/myOrders/602a74164f9ff6408aad5da6").header("Authorization", token))
            .andExpect { status(HttpStatus.OK) }
            .andReturn()

        assertEquals(expectedResponse, result.response.contentAsString)
    }

    @Test
    fun `should create a new order`(){

        val expectedResponse = "{\"id\":\"602b936938e5ee596440a817\",\"products\":[{\"id\":\"602b936938e5ee596440a811\",\"name\":\"Handy\",\"description\":\"Bestes Handy\",\"price\":255.0,\"category\":\"SMARTPHONE\"}],\"orderDate\":\"9.6.2021\",\"price\":1475.0,\"userId\":\"602a74164f9ff6408aad5da6\"}"
        val requestBody = "{\"id\": \"602b936938e5ee596440a817\", \"orderDate\": \"9.6.2021\",\"price\": 1475,\"products\": [{\"id\":\"602b936938e5ee596440a811\",\"name\":\"Handy\",\"description\":\"Bestes Handy\",\"price\":255.0,\"category\":\"SMARTPHONE\"}],\"userId\": \"602a74164f9ff6408aad5da6\"}"

        val result = mockMvc.perform(post("/orders")
                .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                )
            .andExpect { status(HttpStatus.OK) }
            .andReturn()

        assertEquals(expectedResponse, result.response.contentAsString)
    }

    @Test
    fun `should create a new order with empty requestbody`(){

        val expectedResponse = ""
        val requestBody = ""

        val result = mockMvc.perform(post("/orders")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
        )
            .andExpect { status(HttpStatus.BAD_REQUEST) }
            .andReturn()

        assertEquals(expectedResponse, result.response.contentAsString)
    }
}

