package src.main.kotlin.de.novatec.productservice.controller

import com.expediagroup.graphql.spring.operations.Mutation
import src.main.kotlin.de.novatec.productservice.model.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import src.main.kotlin.de.novatec.productservice.service.ProductService

@Component
class ProductMutation : Mutation {

    @Autowired
    private lateinit var productService: ProductService

    fun editProduct(product: Product?): Product? {
        return product?.let { productService.updateProduct(it) }
    }

    fun createProduct(product: Product?): Product? {
        return product?.let { productService.createProduct(it) }
    }

    fun deleteProduct(productId: String): String? {
        var deletedProduct = productService.getProductById(productId)
        productService.deleteProduct(productId)
        return ("Product deleted: $deletedProduct")
    }

}