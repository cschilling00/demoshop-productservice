package src.main.kotlin.de.novatec.productservice.configuration

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter() : OncePerRequestFilter() {
    private val AUTHORIZATION_HEADER = "Authorization"

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        val token = getToken(request).also { println("token: " + it) }
        var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.setBearerAuth(token.toString())
        val request2 = HttpEntity("{\"query\": \"query { getAuthorities }\"}", headers)
        val result = RestTemplate().postForEntity("http://localhost:8081/graphql", request2, String::class.java)
        val node: JsonNode = jacksonObjectMapper().readValue(result.body.toString())

        SecurityContextHolder.getContext().authentication =
            JWTPreAuthenticationToken(
                AuthorityUtils.commaSeparatedStringToAuthorityList(node.get("data").get("getAuthorities").asText())
            )
        println(SecurityContextHolder.getContext().authentication)
        filterChain.doFilter(request, response)
    }

    private fun getToken(request: HttpServletRequest): String? {
        return request.getHeader(AUTHORIZATION_HEADER)
            ?.split("Bearer ")
            ?.last()
    }
}
