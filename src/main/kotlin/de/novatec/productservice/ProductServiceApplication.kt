package src.main.kotlin.de.novatec.productservice

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import src.main.kotlin.de.novatec.productservice.model.Category
import src.main.kotlin.de.novatec.productservice.model.Order
import src.main.kotlin.de.novatec.productservice.model.Product
import src.main.kotlin.de.novatec.productservice.repository.OrderRepository
import src.main.kotlin.de.novatec.productservice.repository.ProductRepository

@SpringBootApplication
class ProductServiceApplication(var productRepository: ProductRepository, var orderRepository: OrderRepository) : CommandLineRunner {
	override fun run(vararg args: String?) {
		productRepository.deleteAll()
		val product1 = Product("602b936938e5ee596440a811", "Handy", "Device to telephone and do other things", 255f, Category.SMARTPHONE)
		val product2 = Product("602b936938e5ee596440a812", "iPod", "Device to listen to music or play games", 355f, Category.MP3)
		productRepository.save(product1)
		productRepository.save(product2)
		println(productRepository.findAll())

		orderRepository.deleteAll()
		orderRepository.save(Order("602b936938e5ee596440a813", listOf(product1, product2), "9.2.2021", 610f, "602a74164f9ff6408aad5da6"))
		orderRepository.save(Order("602b936938e5ee596440a814", listOf(product2), "8.2.2021", 255f, "602a74164f9ff6408aad5da7"))
		println(orderRepository.findAll())
	}
}

fun main(args: Array<String>) {

	runApplication<ProductServiceApplication>(*args) {}
}

