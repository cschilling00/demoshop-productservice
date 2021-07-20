package src.main.kotlin.de.novatec.productservice.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import src.main.kotlin.de.novatec.productservice.model.Order
import src.main.kotlin.de.novatec.productservice.service.OrderService

@RestController
@RequestMapping("orders")
class OrderController(
    val orderService: OrderService
) {
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/myOrders/{id}")
    fun getOrderByUserId(@PathVariable id: String): List<Order?> {
        return orderService.getOrderByUserId(id)
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user')")
    fun createOrder(@RequestBody order: Order?): Order? {
        return order?.let { orderService.createOrder(it) }
    }
}
