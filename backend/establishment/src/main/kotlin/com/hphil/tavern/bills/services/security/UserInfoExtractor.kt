package com.hphil.tavern.bills.services.security

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor
import org.springframework.stereotype.Component

@Component
class UserInfoExtractor: PrincipalExtractor {

    override fun extractPrincipal(map: MutableMap<String, Any>): Any {
        return UserInfo(
            map["id"] as String,
            map["username"] as String,
            map["displayName"] as String,
        )
    }
}