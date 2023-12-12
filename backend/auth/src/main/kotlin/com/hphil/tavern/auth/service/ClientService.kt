package com.hphil.tavern.auth.service

import com.hphil.tavern.auth.model.Client
import com.hphil.tavern.auth.service.repository.Repository
import io.javalin.http.Context

class ClientService(
    private val repository: Repository
) {

    fun clientFromRequestValidateSecret(context: Context): Client {
        val basicAuth = context.basicAuthCredentials()

        val clientId: String
        val clientSecret: String
        if (basicAuth != null) {
            clientId = basicAuth.username
            clientSecret = basicAuth.password
        } else {
            clientId = context.formParam("client_id") ?: error("client_id required")
            clientSecret = context.formParam("client_secret") ?: error("client_secret required")
        }
        val client = repository.findClient(clientId) ?: error("invalid client")
        assert(client.secret == clientSecret) { "invalid secret" }
        return client
    }

    fun clientFromRequest(context: Context): Client {
        val clientId = context.basicAuthCredentials()?.username
            ?: context.formParam("client_id")
            ?: error("client_id required")
        return repository.findClient(clientId) ?: error("invalid client")
    }
}