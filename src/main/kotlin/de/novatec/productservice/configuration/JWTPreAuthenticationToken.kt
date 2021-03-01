package src.main.kotlin.de.novatec.productservice.configuration

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken

class JWTPreAuthenticationToken(authorities: List<GrantedAuthority>) :
        PreAuthenticatedAuthenticationToken(null,null,authorities) {


    override fun getCredentials(): Any? {
        return null
    }
}