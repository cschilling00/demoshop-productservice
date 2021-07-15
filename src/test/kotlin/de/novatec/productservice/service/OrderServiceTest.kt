package src.test.kotlin.de.novatec.orderservice.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
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
    fun `Service should get all orders from mocked Repository`() {
        val orders = listOf(Order("602b936938e5ee596440a813", listOf(Product("602b936938e5ee596440a811", "Handy", "Bestes Handy", 255f, Category.SMARTPHONE), Product("602b936938e5ee596440a812", "iPod", "Bester iPod", 355f, Category.MP3)), "9.2.2021", 610f, "602a74164f9ff6408aad5da6"), Order("602b936938e5ee596440a814", listOf(Product("602b936938e5ee596440a812", "iPod", "Bester iPod", 355f, Category.MP3)), "8.2.2021", 810f, "602a74164f9ff6408aad5da7"))
        every { orderRepository.findAll() } returns orders
        val serviceResult = orderService.getOrder()
        assertEquals(orderRepository.findAll(), serviceResult)
    }

    @Test
    fun `Service should get order with id 602b936938e5ee596440a813 from mocked Repository`() {
        var order = Order("602b936938e5ee596440a813", listOf(Product("602b936938e5ee596440a811", "Handy", "Bestes Handy", 255f, Category.SMARTPHONE), Product("602b936938e5ee596440a812", "iPod", "Bester iPod", 355f, Category.MP3)), "9.2.2021", 610f, "602a74164f9ff6408aad5da6")
        every { orderRepository.findById("602b936938e5ee596440a813") } returns Optional.ofNullable(order) as Optional<Order?>
        val serviceResult = orderService.getOrderById("602b936938e5ee596440a813")
        assertEquals(order as Order?, serviceResult)
    }

    @Test
    fun `Service should throw exception while trying to get non existing order from mocked Repository`() {
        val order = Optional.ofNullable(null)
        every { orderRepository.findById("602b936938e5ee596440a813") } returns order as Optional<Order?>
        assertThrows(NoSuchElementException::class.java, { orderService.getOrderById("602b936938e5ee596440a813") })
    }

    @Test
    fun `Service should get all orders with userId 602a74164f9ff6408aad5da6 from mocked Repository`() {
        val order = listOf<Order>(Order("602b936938e5ee596440a813", listOf(Product("602b936938e5ee596440a811", "Handy", "Bestes Handy", 255f, Category.SMARTPHONE), Product("602b936938e5ee596440a812", "iPod", "Bester iPod", 355f, Category.MP3)), "9.2.2021", 610f, "602a74164f9ff6408aad5da6"))
        every { orderRepository.getOrderByUserId("602a74164f9ff6408aad5da6") } returns order
        val serviceResult = orderService.getOrderByUserId("602a74164f9ff6408aad5da6")
        assertEquals(order, serviceResult)
    }

    @Test
    fun `Service should create order for user with userId 602a74164f9ff6408aad5da6 from mocked Repository`() {
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
    fun `Service should throw exception while trying to create order with non existing products from mocked Repository`() {
        val order = Order("602b936938e5ee596440a813", listOf(Product("602b936938e5ee596440a811", "Handy", "Bestes Handy", 255f, Category.SMARTPHONE)), "9.2.2021", 610f, "602a74164f9ff6408aad5da6")
        every { orderRepository.save(order) } returns order
        every { productRepository.findById("602b936938e5ee596440a811") } returns Optional.ofNullable(null) as Optional<Product?>
        assertThrows(NoSuchElementException::class.java, { orderService.createOrder(order) })
    }
}