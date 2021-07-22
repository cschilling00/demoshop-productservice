package src.main.kotlin.de.novatec.productservice.controller

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import src.main.kotlin.de.novatec.productservice.model.Order
import src.main.kotlin.de.novatec.productservice.service.OrderService

@Component
class OrderMutation(@Autowired private val orderService: OrderService) : GraphQLMutationResolver {



    @PreAuthorize("hasAuthority('user')")
    fun createOrder(order: Order?): Order? {
        return order?.let { orderService.createOrder(it) }
    }
}
