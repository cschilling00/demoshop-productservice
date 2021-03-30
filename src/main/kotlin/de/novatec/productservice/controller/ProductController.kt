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
    @PostMapping
    @PreAuthorize("hasAuthority('user')")
    fun createProduct(@RequestBody product: Product?): Product? {
        return product?.let { productService.createProduct(it) }
    }

    @PreAuthorize("hasAuthority('user')")
    @PatchMapping
    fun editProduct(@RequestBody product: Product?): Product? {
        return product?.let { productService.updateProduct(it) }
    }

    @PreAuthorize("hasAuthority('user')")
    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: String): String? {
        return productService.deleteProduct(id)
    }
}
