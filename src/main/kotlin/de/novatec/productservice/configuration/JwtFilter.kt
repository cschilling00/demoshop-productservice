package src.main.kotlin.de.novatec.productservice.configuration

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
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
        println(headers)

        val request2 = HttpEntity(null, headers)
        val result = RestTemplate().exchange("http://localhost:8081/users/authorities",HttpMethod.GET,request2,
            String::class.java)
    println(result)
        SecurityContextHolder.getContext().authentication =
            JWTPreAuthenticationToken(
                AuthorityUtils.commaSeparatedStringToAuthorityList(result.body.toString())
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
