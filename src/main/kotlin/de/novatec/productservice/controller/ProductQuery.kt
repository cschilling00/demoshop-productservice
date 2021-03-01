package src.main.kotlin.de.novatec.productservice.controller

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import src.main.kotlin.de.novatec.productservice.model.Product
import src.main.kotlin.de.novatec.productservice.service.ProductService

@Component
class ProductQuery : GraphQLQueryResolver {

    @Autowired
    private lateinit var productService: ProductService

    fun getProducts(): List<Product?> {
        return productService.getProduct()
    }

    fun getProductById(id: String): Product? {
        return productService.getProductById(id)
    }
}
