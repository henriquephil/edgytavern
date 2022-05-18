package com.hphil.tavern.auth.service.repository

import com.hphil.tavern.auth.model.Client
import com.hphil.tavern.auth.model.Token
import com.hphil.tavern.auth.model.User

interface Repository {
    fun addClient(client: Client)
    fun findClient(clientId: String, clientSecret: String): Client?

    fun addUser(user: User)
    fun updateUserPassword(user: User)
    fun findUserById(id: String): User?
    fun findUserByUsername(username: String): User?

    fun addToken(token: Token)
    fun findTokenByAccessToken(accessToken: String): Token?
    fun findTokenByRefreshToken(refreshToken: String): Token?
    fun deleteTokenByAccessToken(accessToken: String)
}