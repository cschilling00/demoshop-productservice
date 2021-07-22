package src.test.kotlin.de.novatec.orderservice.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import src.main.kotlin.de.novatec.productservice.model.Category
import src.main.kotlin.de.novatec.productservice.model.Order
import src.main.kotlin.de.novatec.productservice.model.Product
import src.main.kotlin.de.novatec.productservice.repository.OrderRepository
import src.main.kotlin.de.novatec.productservice.repository.ProductRepository
import src.main.kotlin.de.novatec.productservice.service.OrderService
import java.util.*
import kotlin.NoSuchElementException

@ExtendWith(
    MockKExtension::class)
internal class OrderServiceTest{

    @MockK
    private val orderRepository = mockk<OrderRepository>()

    @MockK
    private val productRepository = mockk<ProductRepository>()

    @InjectMockKs
    lateinit var orderService: OrderService

    @Test
    fun `should get all orders with userId 602a74164f9ff6408aad5da6 from mocked Repository`() {
        val order = listOf<Order>(Order("602b936938e5ee596440a813", listOf(Product("602b936938e5ee596440a811", "Handy", "Bestes Handy", 255f, Category.SMARTPHONE), Product("602b936938e5ee596440a812", "iPod", "Bester iPod", 355f, Category.MP3)), "9.2.2021", 610f, "602a74164f9ff6408aad5da6"))
        every { orderRepository.getOrderByUserId("602a74164f9ff6408aad5da6") } returns order
        val serviceResult = orderService.getOrderByUserId("602a74164f9ff6408aad5da6")
        assertEquals(order, serviceResult)
    }

    @Test
    fun `should create order for user with userId 602a74164f9ff6408aad5da6 from mocked Repository`() {
        val order = (Order("602b936938e5ee596440a813", listOf(Product("602b936938e5ee596440a811", "Handy", "Bestes Handy", 255f, Category.SMARTPHONE), Product("602b936938e5ee596440a812", "iPod", "Bester iPod", 355f, Category.MP3)), "9.2.2021", 610f, "602a74164f9ff6408aad5da6"))
        var product11 = Product("602b936938e5ee596440a811", "Handy", "Device to telephone and do other things", 255f, Category.SMARTPHONE)
        var product12 = Product("602b936938e5ee596440a812", "Handy", "Device to telephone and do other things", 255f, Category.SMARTPHONE)
        every { orderRepository.save(order) } returns order
        every { productRepository.findById("602b936938e5ee596440a811") } returns Optional.ofNullable(product11) as Optional<Product?>
        every { productRepository.findById("602b936938e5ee596440a812") } returns Optional.ofNullable(product12) as Optional<Product?>
        val serviceResult = orderService.createOrder(order)
        assertEquals(order, serviceResult)
    }

    @Test
    fun `should throw exception while trying to create order with non existing products from mocked Repository`() {
        val order = Order("602b936938e5ee596440a813", listOf(Product("602b936938e5ee596440a811", "Handy", "Bestes Handy", 255f, Category.SMARTPHONE)), "9.2.2021", 610f, "602a74164f9ff6408aad5da6")
        every { orderRepository.save(order) } returns order
        every { productRepository.findById("602b936938e5ee596440a811") } returns Optional.ofNullable(null) as Optional<Product?>
        assertThrows(NoSuchElementException::class.java, { orderService.createOrder(order) })
    }
}