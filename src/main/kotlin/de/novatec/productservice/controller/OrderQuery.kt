package src.main.kotlin.de.novatec.productservice.controller

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import src.main.kotlin.de.novatec.productservice.model.Order
import src.main.kotlin.de.novatec.productservice.service.OrderService

@Component
class OrderQuery : Query {

    @Autowired
    private lateinit var orderService: OrderService

    fun getOrder(): List<Order?> {
        return orderService.getOrder()
    }

    fun getOrderById(id: String): Order? {
        return orderService.getOrderById(id)
    }
}
