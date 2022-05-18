package com.hphil.tavern.auth.handler

import com.hphil.tavern.auth.handler.dto.UserInfoResponse
import com.hphil.tavern.auth.model.TokenType
import com.hphil.tavern.auth.service.Constants
import com.hphil.tavern.auth.service.repository.Repository
import io.javalin.http.Context
import java.time.LocalDateTime

class UserInfoHandler(
    private val repository: Repository
) {
    fun handle(context: Context) {
        val fullAuthHeader = context.header(Constants.HEADER_AUTHORIZATION) ?: error("user not authenticated")
        val values = fullAuthHeader.split(" ")
        if (values.size < 2)
            error("missing header")
        if (!values[0].equals(TokenType.BEARER.text, true))
            error("invalid header")

        // TODO improve to hit database once
        val token = repository.findTokenByAccessToken(values[1]) ?: error("access token does not exist")
        if (token.accessTokenExpiration.isBefore(LocalDateTime.now())) {
            error("access token expired")
        }
        val user = repository.findUserById(token.userId) ?: error("user not found")
        context.json(UserInfoResponse(user))
    }

}