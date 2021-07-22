package src.test.kotlin.de.novatec.productservice.controller

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.containing
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.graphql.spring.boot.test.GraphQLTestTemplate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import src.main.kotlin.de.novatec.productservice.ProductServiceApplication
import src.main.kotlin.de.novatec.productservice.model.Category
import src.main.kotlin.de.novatec.productservice.model.Order
import src.main.kotlin.de.novatec.productservice.model.Product
import src.main.kotlin.de.novatec.productservice.repository.OrderRepository
import src.main.kotlin.de.novatec.productservice.repository.ProductRepository
import src.test.kotlin.de.novatec.productservice.WireMockInitializer
import java.io.File
import java.nio.charset.Charset


@ExtendWith(
    SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers =  [WireMockInitializer::class])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [ProductServiceApplication::class])
internal class ProductQueryTest(@Autowired val graphQLTestTemplate: GraphQLTestTemplate,
                                @Autowired val productRepository: ProductRepository,
                                @Autowired val orderRepository: OrderRepository,
                                @Autowired val wireMockServer: WireMockServer) {

    @BeforeAll
    fun setHeaderForUser() {
        graphQLTestTemplate.addHeader(
            "Authorization",
            "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaXNzIjoicHJvZHVjdHNlcnZpY2UtYXBpIiwiaWQiOiI2MDJhNzQxNjRmOWZmNjQwOGFhZDVkYTYiLCJpYXQiOjE2MjU2ODAzNjN9.TIm2oPpBxcMqVh8lfV_0aj-bcLgK84jn2HJ0wpchZRzWyu-DkozJr5QkPMwCPPBnnYjUQIM1C5c0WjBKgCEgAQ"
        )
    }

    @BeforeEach
    fun callUsermanagement(){
        wireMockServer.stubFor(
            WireMock.post("/graphql")
                .withRequestBody(equalTo("{\"query\": \"query { getAuthorities }\"}"))
                .withHeader("Authorization", containing("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaXNzIjoicHJvZHVjdHNlcnZpY2UtYXBpIiwiaWQiOiI2MDJhNzQxNjRmOWZmNjQwOGFhZDVkYTYiLCJpYXQiOjE2MjU2ODAzNjN9.TIm2oPpBxcMqVh8lfV_0aj-bcLgK84jn2HJ0wpchZRzWyu-DkozJr5QkPMwCPPBnnYjUQIM1C5c0WjBKgCEgAQ"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody("{\n" +
                            "    \"data\": {\n" +
                            "        \"getAuthorities\": \"user\"\n" +
                            "    }\n" +
                            "}")
                    )
        );
    }

    @AfterEach
    fun afterEach() {
        wireMockServer.resetAll()
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
    fun `should get all products`(){
        var response = graphQLTestTemplate.postForResource("request/products.graphql")
//            println("response: "+response.rawResponse.body.toString())
        val expectedResponse = File("src/test/resources/response/productsRes.json").readText(Charset.defaultCharset())
        Assertions.assertNotNull(response)
        Assertions.assertTrue(response.isOk)
        JSONAssert.assertEquals(expectedResponse, response.rawResponse.body, true)
    }

    @Test
    fun `should get product with id 602b936938e5ee596440a811`(){
        var response = graphQLTestTemplate.postForResource("request/productById.graphql")
        val expectedResponse = File("src/test/resources/response/productByIdRes.json").readText(Charset.defaultCharset())
        Assertions.assertNotNull(response)
        Assertions.assertTrue(response.isOk)
        JSONAssert.assertEquals(expectedResponse, response.rawResponse.body, true)


    }
}
