package src.main.kotlin.de.novatec.productservice.configuration

import com.oembedler.moon.graphql.boot.error.ThrowableGraphQLError
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.RuntimeException

class GraphQLExceptionHandler {
    @ExceptionHandler(RuntimeException::class)
    fun handleGenericException(ex: RuntimeException): ThrowableGraphQLError {
        return ThrowableGraphQLError(ex)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ThrowableGraphQLError {
        return ThrowableGraphQLError(ex)
    }
}

@Configuration
class GraphQLConfiguration {
    @Bean
    fun graphQLExceptionHandler(): GraphQLExceptionHandler {
        return GraphQLExceptionHandler()
    }
}
