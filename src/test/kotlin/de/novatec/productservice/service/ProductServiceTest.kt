package src.test.kotlin.de.novatec.productservice.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import src.main.kotlin.de.novatec.productservice.model.Category
import src.main.kotlin.de.novatec.productservice.model.Product
import src.main.kotlin.de.novatec.productservice.repository.ProductRepository
import src.main.kotlin.de.novatec.productservice.service.ProductService
import java.util.*
import kotlin.NoSuchElementException

@ExtendWith(
    MockKExtension::class)
internal class ProductServiceTest{

    @MockK
    private val productRepository = mockk<ProductRepository>()

    @InjectMockKs
    lateinit var productService: ProductService

    @Test
    fun `Service should get all products from mocked Repository`() {
        val products = listOf<Product>(Product("602b936938e5ee596440a811", "Handy", "Device to telephone and do other things", 255f, Category.SMARTPHONE), Product("602b936938e5ee596440a812", "iPod", "Device to listen to music or play games", 355f, Category.MP3))
        every { productRepository.findAll() } returns products
        val serviceResult = productService.getProduct()
        assertEquals(productRepository.findAll(), serviceResult)
    }

    @Test
    fun `Service should get product with id 602b936938e5ee596440a811 from mocked Repository`() {
        var product = Product("602b936938e5ee596440a811", "Handy", "Device to telephone and do other things", 255f, Category.SMARTPHONE)
        every { productRepository.findById("602b936938e5ee596440a811") } returns Optional.ofNullable(product) as Optional<Product?>
        val serviceResult = productService.getProductById("602b936938e5ee596440a811")
        assertEquals(product as Product?, serviceResult)
    }

    @Test
    fun `Service should throw exception while trying to get non existing product from mocked Repository`() {
        val product = Optional.ofNullable(null)
        every { productRepository.findById("602b936938e5ee596440a811") } returns product as Optional<Product?>
        assertThrows(NoSuchElementException::class.java, { productService.getProductById("602b936938e5ee596440a811") })
    }

}