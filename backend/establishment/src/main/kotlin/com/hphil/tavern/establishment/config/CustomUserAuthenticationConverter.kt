package com.hphil.tavern.establishment.config

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter
import org.springframework.stereotype.Component

@Component
class CustomUserAuthenticationConverter : UserAuthenticationConverter {
    override fun convertUserAuthentication(userAuthentication: Authentication?): MutableMap<String, *> {
        TODO("Not yet implemented")
    }

    override fun extractAuthentication(map: MutableMap<String, *>): Authentication? {
        if (map.containsKey(UserAuthenticationConverter.USERNAME)) {
            return UsernamePasswordAuthenticationToken(Principal(map["username"] as String, map["sub"] as String, ""), "N/A", emptySet())
        }
        return null
    }
}

class Principal(val username: String, val uid: String, private val name: String): java.security.Principal {
    override fun getName(): String {
        return name
    }

}