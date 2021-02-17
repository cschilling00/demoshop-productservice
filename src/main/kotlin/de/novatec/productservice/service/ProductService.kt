package src.main.kotlin.de.novatec.productservice.service

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import src.main.kotlin.de.novatec.productservice.model.Product
import src.main.kotlin.de.novatec.productservice.repository.ProductRepository

@Service
class ProductService {

    @Autowired
    private lateinit var productRepository: ProductRepository

    fun getProductById(id: String): Product? {
        return productRepository.findById(id).orElseThrow {
            throw NoSuchElementException("Product with id ´$id´ not found")
        }
    }

    fun getProduct(): List<Product?> {
        return productRepository.findAll()
    }

    fun updateProduct(product: Product): Product? {
        if (product.id == null) {
            throw IllegalArgumentException("No Id given")
        } else {
            productRepository.findById(product.id).orElseThrow {
                throw NoSuchElementException("Order with id ´$product.id´ not found")
            }
            return productRepository.save(product)
        }
    }

    fun createProduct(product: Product): Product {
        return productRepository.save(product)
    }

    fun deleteProduct(productId: String): String {
        productRepository.findById(productId).orElseThrow {
            throw NoSuchElementException("Product with id ´$productId´ not found")
        }
        productRepository.deleteById(productId)
        return "Product successfully deleted"
    }
}
