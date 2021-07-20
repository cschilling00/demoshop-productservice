package src.main.kotlin.de.novatec.productservice.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import src.main.kotlin.de.novatec.productservice.model.Product
import src.main.kotlin.de.novatec.productservice.service.ProductService

@RestController
@RequestMapping("products")
class ProductController(
    val productService: ProductService
) {

    @PreAuthorize("permitAll()")
    @GetMapping
    fun getProducts(): List<Product?> {
        return productService.getProduct()
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: String): Product? {
        return productService.getProductById(id)
    }
}
