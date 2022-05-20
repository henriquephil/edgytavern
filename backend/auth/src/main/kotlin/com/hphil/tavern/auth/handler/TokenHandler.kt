package com.hphil.tavern.auth.handler

import com.hphil.tavern.auth.model.IdentityProvider
import com.hphil.tavern.auth.model.User
import com.hphil.tavern.auth.service.AccessTokenService
import com.hphil.tavern.auth.service.ClientService
import com.hphil.tavern.auth.service.encoder.PasswordEncoder
import com.hphil.tavern.auth.service.repository.Repository
import io.javalin.http.Context
import java.time.LocalDateTime

class TokenHandler(
    private val repository: Repository,
    private val passwordEncoder: PasswordEncoder,
    private val clientService: ClientService,
    private val accessTokenService: AccessTokenService
) {
    fun handle(context: Context) {
        val client = clientService.clientFromRequestValidateSecret(context)
        val user = when (context.formParam("grant_type") ?: error("grant_type required")) {
            "password" -> handlePassword(context)
            "refresh_token" -> handleRefreshToken(context)
            else -> error("invalid grant_type")
        }
        context.json(accessTokenService.createAccessToken(user, client))
    }

    private fun handlePassword(context: Context): User {
        val username = context.formParam("username") ?: error("username required")
        val password = context.formParam("password") ?: error("password required")

        val user = repository.findUserByUsernameAndProvider(username, IdentityProvider.SELF) ?: error("user does not exist")
        if (!passwordEncoder.matches(password, user.password!!)) {
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
}