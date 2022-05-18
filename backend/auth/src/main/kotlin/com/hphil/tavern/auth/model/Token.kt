package com.hphil.tavern.auth.model

import com.hphil.tavern.auth.service.TokenConfig
import java.time.LocalDateTime
import java.util.*

data class Token(
    val userId: String,
    val clientId: String,
    val tokenType: TokenType,
    val accessToken: String,
    val accessTokenExpiration: LocalDateTime,
    val refreshToken: String? = null,
    val refreshTokenExpiration: LocalDateTime? = null
) {
    companion object {
        fun new(tokenConfig: TokenConfig, user: User, client: Client): Token {
            return Token(
                user.id,
                client.id,
                TokenType.BEARER,
                tokenConfig.generateToken(),
                LocalDateTime.now().plusSeconds(tokenConfig.accessTokenTtl),
                UUID.randomUUID().toString(),
                tokenConfig.refreshTokenTtl?.let { LocalDateTime.now().plusSeconds(it) }
            )
        }
    }
}