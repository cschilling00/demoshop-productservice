package src.main.kotlin.de.novatec.productservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Product(
    @Id
    val id: String?,
    val name: String,
    val description: String,
    val price: Int,
    val category: Category
)
