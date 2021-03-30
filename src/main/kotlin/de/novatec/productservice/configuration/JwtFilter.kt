package src.main.kotlin.de.novatec.productservice.configuration

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
        val request2 = HttpEntity("", headers)
        val result = RestTemplate().getForEntity("http://localhost:8081/users/authorities", String::class.java)

        SecurityContextHolder.getContext().authentication =
            JWTPreAuthenticationToken(
                AuthorityUtils.commaSeparatedStringToAuthorityList(result.toString())
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
