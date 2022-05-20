package com.hphil.tavern.auth.model

import java.time.LocalDateTime
import java.util.*

data class User(
    val id: String = UUID.randomUUID().toString(),
    val provider: IdentityProvider,
    val username: String,
    var password: String? = null,
    var active: Boolean = true,
    var displayName: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun withSelfProvider(username: String, password: String, displayName: String) = User(
            provider = IdentityProvider.SELF,
            username = username,
            password = password,
            displayName = displayName
        )
        fun withGoogleProvider(username: String, displayName: String) = User(
            provider = IdentityProvider.GOOGLE,
            username = username,
            displayName = displayName
        )
    }
}
