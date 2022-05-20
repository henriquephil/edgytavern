package com.hphil.tavern.auth.handler

import com.hphil.tavern.auth.handler.dto.NewUserRequest
import com.hphil.tavern.auth.handler.dto.UpdateUserDisplayNameRequest
import com.hphil.tavern.auth.handler.dto.UpdateUserPasswordRequest
import com.hphil.tavern.auth.model.User
import com.hphil.tavern.auth.service.AccessTokenService
import com.hphil.tavern.auth.service.encoder.PasswordEncoder
import com.hphil.tavern.auth.service.repository.Repository
import com.hphil.tavern.auth.utils.TokenExtractor
import io.javalin.http.Context

class UserHandler(
    private val repository: Repository,
    private val accessTokenService: AccessTokenService,
    private val encoder: PasswordEncoder
) {

    fun insert(context: Context) {
        val user = context.bodyAsClass(NewUserRequest::class.java)
        repository.addUser(
            User.withSelfProvider(user.username, encoder.encode(user.password), user.displayName)
        )
    }

    fun updatePassword(context: Context) {
        val user = accessTokenService.findUserByToken(TokenExtractor.extract(context))
        val body = context.bodyAsClass(UpdateUserPasswordRequest::class.java)
        if (!encoder.matches(body.oldPassword, user.password!!))
            error("invalid password")
        user.password = encoder.encode(body.newPassword)
        repository.updateUserPassword(user)
    }

    fun updateDisplayName(context: Context) {
        val user = accessTokenService.findUserByToken(TokenExtractor.extract(context))
        val body = context.bodyAsClass(UpdateUserDisplayNameRequest::class.java)
        user.displayName = body.displayName
        repository.updateUserDisplayName(user)
    }
}
