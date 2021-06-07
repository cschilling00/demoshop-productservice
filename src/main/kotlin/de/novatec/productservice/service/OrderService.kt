package src.main.kotlin.de.novatec.productservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import src.main.kotlin.de.novatec.productservice.model.Order
import src.main.kotlin.de.novatec.productservice.repository.OrderRepository
import src.main.kotlin.de.novatec.productservice.repository.ProductRepository

@Service
class OrderService {

    @Autowired
    private lateinit var orderRepository: OrderRepository
    @Autowired
    private lateinit var productRepository: ProductRepository

    fun getOrderById(id: String): Order? {
        return orderRepository.findById(id).orElseThrow {
            throw NoSuchElementException("Order with id ´$id´ not found")
        }
    }

    fun getOrderByUserId(id: String): List<Order?> {
        return orderRepository.getOrderByUserId(id)
    }

    fun getOrder(): List<Order?> {
        return orderRepository.findAll()
    }

    fun updateOrder(order: Order): Order? {
        if (order.id == "") {
            throw IllegalArgumentException("No Id given")
        } else {
            order.id?.let {
                orderRepository.findById(it).orElseThrow {
                    throw NoSuchElementException("Order with id ´${order.id}´ not found")
                }
            }
            return orderRepository.save(order)
        }
    }

    fun createOrder(order: Order): Order {
        order.products?.filterNotNull()?.forEach {
            productRepository.findById(it.id).orElseThrow { NoSuchElementException("Product with id ´${it.id}´ not found") }
        }
        return orderRepository.save(order)
    }

    fun deleteOrder(orderId: String): String {
        orderRepository.findById(orderId).orElseThrow {
            throw NoSuchElementException("Order with id ´$orderId´ not found")
        }
        orderRepository.deleteById(orderId)
        return "Order successfully deleted"
    }
}
