package com.hphil.tavern.auth.service

import com.hphil.tavern.auth.handler.dto.RefreshTokenResponse
import com.hphil.tavern.auth.model.Client
import com.hphil.tavern.auth.model.Token
import com.hphil.tavern.auth.model.User
import com.hphil.tavern.auth.service.repository.Repository
import java.time.LocalDateTime

class AccessTokenService(
    private val repository: Repository,
    private val tokenConfig: TokenConfig
) {

    fun findUserByToken(accessToken: String): User {
        val token = repository.findTokenByAccessToken(accessToken) ?: error("token not found")
        if (token.accessTokenExpiration.isAfter(LocalDateTime.now()))
            error("token expired")
        return repository.findUserById(token.userId) ?: error("user not found")
    }

    fun createAccessToken(user: User, client: Client): RefreshTokenResponse {
        val token = Token.new(tokenConfig, user, client)
        repository.addToken(token)
        return RefreshTokenResponse(
            token.accessToken,
            token.tokenType.text,
            tokenConfig.accessTokenTtl,
            token.refreshToken,
            tokenConfig.refreshTokenTtl
        )
    }
}