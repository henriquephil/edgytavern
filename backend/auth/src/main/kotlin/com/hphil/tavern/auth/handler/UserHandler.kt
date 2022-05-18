package com.hphil.tavern.auth.handler

import com.hphil.tavern.auth.handler.dto.NewUserRequest
import com.hphil.tavern.auth.handler.dto.PasswordUpdateRequest
import com.hphil.tavern.auth.model.User
import com.hphil.tavern.auth.service.encoder.PasswordEncoder
import com.hphil.tavern.auth.service.repository.Repository
import com.hphil.tavern.auth.utils.TokenExtractor
import io.javalin.http.Context

class UserHandler(
    private val repository: Repository,
    private val encoder: PasswordEncoder
) {

    fun insert(context: Context) {
        val user = context.bodyAsClass(NewUserRequest::class.java)
        repository.addUser(User(
            username = user.username,
            password = encoder.encode(user.password),
            displayName = user.displayName
        ))
    }

    fun updatePassword(context: Context) {
        val body = context.bodyAsClass(PasswordUpdateRequest::class.java)
        val accessToken = TokenExtractor.extract(context)
        val token =
            repository.findTokenByAccessToken(accessToken) ?: error("token not found") // TODO findUserByAccessToken?
        val user = repository.findUserById(token.userId) ?: error("user not found")
        if (!encoder.matches(body.oldPassword, user.password))
            error("invalid password")
        user.password = encoder.encode(body.newPassword)
        repository.updateUserPassword(user)
    }
}
