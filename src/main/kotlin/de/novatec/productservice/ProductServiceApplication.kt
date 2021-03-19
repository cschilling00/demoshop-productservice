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
		productRepository.save(Product("602b936938e5ee596440a811", "Handy", "Bestes Handy", 255f, Category.SMARTPHONE))
		productRepository.save(Product("602b936938e5ee596440a812", "iPod", "Bester iPod", 355f, Category.MP3))
		println(productRepository.findAll())

		orderRepository.deleteAll()
		orderRepository.save(Order("602b936938e5ee596440a813", listOf(Product("602b936938e5ee596440a811", "Handy", "Bestes Handy", 255f, Category.SMARTPHONE), Product("602b936938e5ee596440a812", "iPod", "Bester iPod", 355f, Category.MP3)), "9.2.2021", 610f, "602a74164f9ff6408aad5da6"))
		orderRepository.save(Order("602b936938e5ee596440a814", listOf(Product("602b936938e5ee596440a812", "iPod", "Bester iPod", 355f, Category.MP3)), "8.2.2021", 810f, "602a74164f9ff6408aad5da7"))
		println(orderRepository.findAll())
	}
}

fun main(args: Array<String>) {

	runApplication<ProductServiceApplication>(*args) {}
}

