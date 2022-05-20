package com.hphil.tavern.auth.handler

import com.hphil.tavern.auth.handler.dto.GoogleRequest
import com.hphil.tavern.auth.model.IdentityProvider
import com.hphil.tavern.auth.model.User
import com.hphil.tavern.auth.service.AccessTokenService
import com.hphil.tavern.auth.service.repository.Repository
import com.mashape.unirest.http.Unirest
import io.javalin.http.Context
import org.json.JSONObject


class GoogleHandler(
    private val accessTokenService: AccessTokenService,
    private val repository: Repository
) {
    fun handle(context: Context) {
        val body = context.bodyAsClass(GoogleRequest::class.java)

        val gapi = Unirest.get("https://www.googleapis.com/oauth2/v3/userinfo?access_token=${body.accessToken}")
            .asJsonAsync()
        val client = repository.findClient(body.clientId) ?: error("invalid client")
        // TODO client allow google provider

        val jsonResponse = gapi.get().body.`object`
        val sub = jsonResponse.get("sub").toString()
        val user = repository.findUserByUsernameAndProvider(sub, IdentityProvider.GOOGLE) ?: create(sub, jsonResponse)
        context.json(accessTokenService.createAccessToken(user, client))
    }

    private fun create(sub: String, jsonObject: JSONObject): User {
        val user = User.withGoogleProvider(sub, jsonObject.get("name").toString())
        repository.addUser(user)
        return user
    }
}