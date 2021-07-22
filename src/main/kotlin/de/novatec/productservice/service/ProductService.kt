package src.main.kotlin.de.novatec.productservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import src.main.kotlin.de.novatec.productservice.model.Product
import src.main.kotlin.de.novatec.productservice.repository.ProductRepository

@Service
class ProductService(@Autowired val productRepository: ProductRepository) {

    fun getProductById(id: String): Product? {
        return productRepository.findById(id).orElseThrow {
            throw NoSuchElementException("Product with id ´$id´ not found")
        }
    }

    fun getProduct(): List<Product?> {
        return productRepository.findAll()
    }
}
