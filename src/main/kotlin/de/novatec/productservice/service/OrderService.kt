package src.main.kotlin.de.novatec.productservice.service

import org.springframework.beans.factory.annotation.Autowired
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

    fun getOrder(): List<Order?> {
        return orderRepository.findAll()
    }

    fun updateOrder(order: Order): Order? {
        if (order.id == null) {
            throw IllegalArgumentException("No Id given")
        } else {
            orderRepository.findById(order.id).orElseThrow {
                throw NoSuchElementException("Order with id ´$order.id´ not found")
            }
            return orderRepository.save(order)
        }
    }

    fun createOrder(order: Order): Order {
        order.productIds.filterNotNull().forEach {
            productRepository.findById(it).orElseThrow { NoSuchElementException("Product with id ´$it´ not found") }
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
