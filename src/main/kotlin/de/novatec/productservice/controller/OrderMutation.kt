package src.main.kotlin.de.novatec.productservice.controller

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import src.main.kotlin.de.novatec.productservice.model.Order
import src.main.kotlin.de.novatec.productservice.service.OrderService

@Component
class OrderMutation : GraphQLMutationResolver {

    @Autowired
    private lateinit var orderService: OrderService

    fun editOrder(order: Order?): Order? {
        return order?.let { orderService.updateOrder(it) }
    }

    fun createOrder(order: Order?): Order? {
        return order?.let { orderService.createOrder(it) }
    }

    fun deleteOrder(orderId: String): String {
        return orderService.deleteOrder(orderId)
    }
}
