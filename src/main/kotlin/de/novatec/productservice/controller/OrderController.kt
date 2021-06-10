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
    @GetMapping
    fun getOrders(): List<Order?> {
        return orderService.getOrder()
    }

    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id: String): Order? {
        return orderService.getOrderById(id)
    }

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

    @PreAuthorize("hasAuthority('user')")
    @PatchMapping
    fun editOrder(@RequestBody order: Order?): Order? {
        return order?.let { orderService.updateOrder(it) }
    }

    @PreAuthorize("hasAuthority('user')")
    @DeleteMapping("/{id}")
    fun deleteOrder(@PathVariable id: String): String? {
        return orderService.deleteOrder(id)
    }
}
