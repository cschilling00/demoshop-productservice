package src.main.kotlin.de.novatec.productservice.controller

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import src.main.kotlin.de.novatec.productservice.model.Product
import src.main.kotlin.de.novatec.productservice.service.ProductService

@Component
class ProductMutation : GraphQLMutationResolver {

    @Autowired
    private lateinit var productService: ProductService

    fun editProduct(product: Product?): Product? {
        return product?.let { productService.updateProduct(it) }
    }

    fun createProduct(product: Product?): Product? {
        return product?.let { productService.createProduct(it) }
    }

    fun deleteProduct(productId: String): String? {
        return productService.deleteProduct(productId)
    }
}
