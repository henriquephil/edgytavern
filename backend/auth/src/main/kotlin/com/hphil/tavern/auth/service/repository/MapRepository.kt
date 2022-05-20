package com.hphil.tavern.auth.service.repository

import com.hphil.tavern.auth.model.Client
import com.hphil.tavern.auth.model.IdentityProvider
import com.hphil.tavern.auth.model.Token
import com.hphil.tavern.auth.model.User

class MapRepository: Repository {
    private val clientList: MutableList<Client> = mutableListOf()
    private val userList: MutableList<User> = mutableListOf()
    private val tokenList: MutableList<Token> = mutableListOf()

    override fun addClient(client: Client) {
        clientList.add(client)
    }

    override fun findClient(clientId: String): Client? {
        return clientList.find { it.id == clientId }
    }


    override fun addUser(user: User) {
        userList.add(user)
    }

    override fun updateUserPassword(user: User) {
        findUserById(user.id)?.password = user.password
    }

    override fun updateUserDisplayName(user: User) {
        findUserById(user.id)?.displayName = user.displayName
    }

    override fun findUserById(id: String): User? {
        return userList.find { it.id == id }
    }

    override fun findUserByUsernameAndProvider(username: String, provider: IdentityProvider): User? {
        return userList.find { it.username == username && it.provider == provider }
    }


    override fun addToken(token: Token) {
        tokenList.add(token)
    }

    override fun findTokenByAccessToken(accessToken: String): Token? {
        return tokenList.find { it.accessToken == accessToken }
    }

    override fun findTokenByRefreshToken(refreshToken: String): Token? {
        return tokenList.find { it.refreshToken == refreshToken }
    }

    override fun deleteTokenByAccessToken(accessToken: String) {
        tokenList.removeIf { it.accessToken == accessToken }
    }
}
