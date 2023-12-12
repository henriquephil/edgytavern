package com.hphil.tavern.auth.handler

import com.hphil.tavern.auth.handler.dto.UserInfoResponse
import com.hphil.tavern.auth.service.repository.Repository
import io.javalin.http.Context
import java.time.LocalDateTime

class TokenInfoHandler(
    private val repository: Repository
) {
    fun handle(context: Context) {
        val token = context.formParam("token")
        val accessToken = repository.findTokenByAccessToken(token!!) ?: error("access token does not exist")
        if (accessToken.accessTokenExpiration.isAfter(LocalDateTime.now())) {
            error("access token expired")
        }
        val user = repository.findUserById(accessToken.userId) ?: error("user not found")
        context.json(UserInfoResponse(user))
    }

}