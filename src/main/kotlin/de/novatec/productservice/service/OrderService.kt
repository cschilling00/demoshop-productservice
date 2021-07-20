package src.main.kotlin.de.novatec.productservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import src.main.kotlin.de.novatec.productservice.model.Order
import src.main.kotlin.de.novatec.productservice.repository.OrderRepository
import src.main.kotlin.de.novatec.productservice.repository.ProductRepository

@Service
class OrderService(@Autowired val orderRepository: OrderRepository,
    @Autowired val productRepository: ProductRepository) {



    fun getOrderByUserId(id: String): List<Order?> {
        return orderRepository.getOrderByUserId(id)
    }

    fun createOrder(order: Order): Order {
        order.products.filterNotNull().forEach {
            productRepository.findById(it.id).orElseThrow { NoSuchElementException("Product with id ´${it.id}´ not found") }
        }
        return orderRepository.save(order)

    }
}
