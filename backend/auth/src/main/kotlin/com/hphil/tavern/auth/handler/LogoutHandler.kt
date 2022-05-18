package com.hphil.tavern.auth.handler

import com.hphil.tavern.auth.service.repository.Repository
import com.hphil.tavern.auth.utils.TokenExtractor
import io.javalin.http.Context

class LogoutHandler(
    private val repository: Repository
) {

    fun handle(context: Context) {
        val token = TokenExtractor.extract(context)
        repository.deleteTokenByAccessToken(token)
    }
}