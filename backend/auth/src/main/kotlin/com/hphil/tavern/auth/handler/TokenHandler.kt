package com.hphil.tavern.auth.handler

import com.hphil.tavern.auth.handler.dto.RefreshTokenResponse
import com.hphil.tavern.auth.model.Client
import com.hphil.tavern.auth.model.Token
import com.hphil.tavern.auth.model.User
import com.hphil.tavern.auth.service.TokenConfig
import com.hphil.tavern.auth.service.encoder.PasswordEncoder
import com.hphil.tavern.auth.service.repository.Repository
import io.javalin.http.Context
import java.time.LocalDateTime

class TokenHandler(
    private val repository: Repository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenConfig: TokenConfig
) {
    fun handle(context: Context) {
        val client = clientFromRequest(context)
        val user = when (context.formParam("grant_type") ?: error("grant_type required")) {
            "password" -> handlePassword(context)
            "refresh_token" -> handleRefreshToken(context)
            else -> error("invalid grant_type")
        }
        context.json(createAccessToken(user, client))
    }

    private fun clientFromRequest(context: Context): Client {
        val clientId: String
        val clientSecret: String
        if (context.basicAuthCredentialsExist()) {
            clientId = context.basicAuthCredentials().username
            clientSecret = context.basicAuthCredentials().password
        } else {
            clientId = context.formParam("client_id") ?: error("client_id required")
            clientSecret = context.formParam("client_secret") ?: error("client_secret required")
        }
        return repository.findClient(clientId, clientSecret) ?: error("invalid client")
    }

    private fun handlePassword(context: Context): User {
        val username = context.formParam("username") ?: error("username required")
        val password = context.formParam("password") ?: error("password required")

        val user = repository.findUserByUsername(username) ?: error("user does not exist")
        if (!passwordEncoder.matches(password, user.password)) {
            error("Password does not match")
        }
        return user
    }

    private fun handleRefreshToken(context: Context): User {
        val refreshToken = context.formParam("refresh_token") ?: error("refresh_token required")
        val token = repository.findTokenByRefreshToken(refreshToken) ?: error("refresh_token does not exist")
        if (token.refreshTokenExpiration != null && token.refreshTokenExpiration.isAfter(LocalDateTime.now())) {
            error("refresh token expired")
        }
        return repository.findUserById(token.userId) ?: error("user not found")
    }

    private fun createAccessToken(user: User, client: Client): RefreshTokenResponse {
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