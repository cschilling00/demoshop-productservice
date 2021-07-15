package src.main.kotlin.de.novatec.productservice.repository

import src.main.kotlin.de.novatec.productservice.model.Product
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepository : MongoRepository<Product?, String?> {
    override fun findAll(): List<Product?>
}