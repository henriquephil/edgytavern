package com.hphil.tavern.auth.service

import com.hphil.tavern.auth.model.Client
import com.hphil.tavern.auth.service.repository.Repository
import io.javalin.http.Context

class ClientService(
    private val repository: Repository
) {

    fun clientFromRequestValidateSecret(context: Context): Client {
        val clientId: String
        val clientSecret: String
        if (context.basicAuthCredentialsExist()) {
            clientId = context.basicAuthCredentials().username
            clientSecret = context.basicAuthCredentials().password
        } else {
            clientId = context.formParam("client_id") ?: error("client_id required")
            clientSecret = context.formParam("client_secret") ?: error("client_secret required")
        }
        val client = repository.findClient(clientId) ?: error("invalid client")
        assert(client.secret == clientSecret) { "invalid secret" }
        return client
    }

    fun clientFromRequest(context: Context): Client {
        val clientId: String
        if (context.basicAuthCredentialsExist()) {
            clientId = context.basicAuthCredentials().username
        } else {
            clientId = context.formParam("client_id") ?: error("client_id required")
        }
        return repository.findClient(clientId) ?: error("invalid client")
    }
}