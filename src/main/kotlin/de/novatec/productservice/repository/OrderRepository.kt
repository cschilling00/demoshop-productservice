package src.main.kotlin.de.novatec.productservice.repository

import src.main.kotlin.de.novatec.productservice.model.Order
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : MongoRepository<Order?, String?> {
    override fun findAll(): List<Order?>
    fun getOrderByUserId(userId: String): List<Order?>
}