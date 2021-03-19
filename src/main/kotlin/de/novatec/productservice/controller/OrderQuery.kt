package src.main.kotlin.de.novatec.productservice.controller

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import src.main.kotlin.de.novatec.productservice.model.Order
import src.main.kotlin.de.novatec.productservice.service.OrderService

@Component
class OrderQuery : GraphQLQueryResolver {

    @Autowired
    private lateinit var orderService: OrderService

    @PreAuthorize("hasAuthority('admin')")
    fun getOrders(): List<Order?> {
        return orderService.getOrder()
    }

    @PreAuthorize("hasAuthority('user')")
    fun getOrderById(id: String): Order? {
        return orderService.getOrderById(id)
    }

    @PreAuthorize("hasAuthority('user')")
    fun getOrderByUserId(id: String): List<Order?> {
        return orderService.getOrderByUserId(id)
    }
}
