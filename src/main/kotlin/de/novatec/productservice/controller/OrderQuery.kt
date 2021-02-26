package src.main.kotlin.de.novatec.productservice.controller

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import src.main.kotlin.de.novatec.productservice.model.Order
import src.main.kotlin.de.novatec.productservice.service.OrderService

@Component
class OrderQuery : GraphQLQueryResolver {

    @Autowired
    private lateinit var orderService: OrderService

    fun getOrders(): List<Order?> {
        return orderService.getOrder()
    }

    fun getOrderById(id: String): Order? {
        return orderService.getOrderById(id)
    }
}
