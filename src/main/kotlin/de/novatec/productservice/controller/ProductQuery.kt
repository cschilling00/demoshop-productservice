package src.main.kotlin.de.novatec.productservice.controller

import src.main.kotlin.de.novatec.productservice.service.ProductService
import com.expediagroup.graphql.spring.operations.Query
import src.main.kotlin.de.novatec.productservice.model.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class ProductQuery : Query {

    @Autowired
    private lateinit var productService: ProductService

    fun getProduct(): List<Product?> {
        return productService.getProduct()
    }

    fun getProductById(id: String): Product? {
        return productService.getProductById(id)
    }

}
