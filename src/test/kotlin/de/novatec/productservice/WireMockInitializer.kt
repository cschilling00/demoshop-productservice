package src.test.kotlin.de.novatec.productservice

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ContextClosedEvent


class WireMockInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
        val wireMockServer = WireMockServer(WireMockConfiguration().port(8081))
        wireMockServer.start()

        configurableApplicationContext
            .beanFactory
            .registerSingleton("wireMockServer", wireMockServer)

        configurableApplicationContext.addApplicationListener { applicationEvent ->
            if (applicationEvent is ContextClosedEvent) {
                wireMockServer.stop()
            }
        }

        TestPropertyValues
            .of(mapOf("usermanagement_base_url" to "http://localhost:" + wireMockServer.port()))
            .applyTo(configurableApplicationContext)
    }
}