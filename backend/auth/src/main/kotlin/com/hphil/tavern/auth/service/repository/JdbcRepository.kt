package com.hphil.tavern.auth.service.repository

import com.hphil.tavern.auth.model.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp
import javax.sql.DataSource

/**
 * TODO: find a suitable library to replace the queries - it better not require changes in the model classes
 */
class JdbcRepository() : Repository {
    private val dataSource: DataSource

    init {
        dataSource = HikariDataSource(HikariConfig("/datasource.properties"))
    }

    override fun addClient(client: Client) {
        execute(CLIENT_INSERT) {
            it.setString(1, client.id)
            it.setString(2, client.secret)
        }
    }

    override fun findClient(clientId: String): Client? {
        return findOne(CLIENT_FIND_BY_ID, { rs -> this.mapClient(rs) }) {
            it.setString(1, clientId)
        }
    }

    override fun addUser(user: User) {
        execute(USER_INSERT) {
            it.setString(1, user.id)
            it.setString(2, user.provider.toString())
            it.setString(3, user.username)
            it.setString(4, user.password)
            it.setBoolean(5, user.active)
            it.setString(6, user.displayName)
            it.setTimestamp(7, Timestamp.valueOf(user.createdAt))
        }
    }

    override fun updateUserPassword(user: User) {
        execute(USER_UPDATE_PASSWORD) {
            it.setString(1, user.password)
            it.setString(2, user.id)
        }
    }

    override fun updateUserDisplayName(user: User) {
        execute(USER_UPDATE_DISPLAY_NAME) {
            it.setString(1, user.displayName)
            it.setString(2, user.id)
        }
    }

    override fun findUserById(id: String): User? {
        return findOne(USER_FIND_BY_ID, { mapUser(it) }) {
            it.setString(1, id)
        }
    }

    override fun findUserByUsernameAndProvider(username: String, provider: IdentityProvider): User? {
        return findOne(USER_FIND_BY_USERNAME_AND_PROVIDER, { mapUser(it) }) {
            it.setString(1, username)
            it.setString(2, provider.toString())
        }
    }

    override fun addToken(token: Token) {
        execute(TOKEN_INSERT) { stmt ->
            stmt.setString(1, token.userId)
            stmt.setString(2, token.clientId)
            stmt.setString(3, token.tokenType.toString())
            stmt.setString(4, token.accessToken)
            stmt.setTimestamp(5, token.accessTokenExpiration.let { Timestamp.valueOf(it) })
            stmt.setString(6, token.refreshToken)
            stmt.setTimestamp(7, token.refreshTokenExpiration?.let { Timestamp.valueOf(it) })
        }
    }

    override fun findTokenByAccessToken(accessToken: String): Token? {
        return findOne(TOKEN_FIND_BY_ACCESS_TOKEN, { mapToken(it) }) {
            it.setString(1, accessToken)
        }
    }

    override fun findTokenByRefreshToken(refreshToken: String): Token? {
        return findOne(TOKEN_FIND_BY_REFRESH_TOKEN, { mapToken(it) }) {
            it.setString(1, refreshToken)
        }
    }

    override fun deleteTokenByAccessToken(accessToken: String) {
        execute(TOKEN_DELETE) {
            it.setString(1, accessToken)
        }
    }

    private fun mapClient(rs: ResultSet): Client {
        return Client(
            rs.getString("id"),
            rs.getString("secret")
        )
    }

    private fun mapUser(rs: ResultSet): User = User(
        rs.getString("id"),
        IdentityProvider.valueOf(rs.getString("identity_provider")),
        rs.getString("username"),
        rs.getString("password"),
        rs.getBoolean("active"),
        rs.getString("display_name"),
        rs.getTimestamp("created_at").toLocalDateTime()
    )

    private fun mapToken(rs: ResultSet) = Token(
        rs.getString("user_id"),
        rs.getString("client_id"),
        TokenType.valueOf(rs.getString("token_type")),
        rs.getString("access_token"),
        rs.getTimestamp("access_token_expiration").toLocalDateTime(),
        rs.getString("refresh_token"),
        rs.getTimestamp("refresh_token_expiration")?.toLocalDateTime()
    )

    private fun execute(query: String, setParams: (PreparedStatement) -> Unit) {
        dataSource.connection.use { conn ->
            val stmt = conn.prepareStatement(query)
            setParams(stmt)
            stmt.execute()
        }
    }

    private fun <T> findOne(query: String, mapper: (ResultSet) -> T, setParams: (PreparedStatement) -> Unit): T? {
        dataSource.connection.use { conn ->
            val stmt = conn.prepareStatement(query)
            setParams(stmt)
            stmt.executeQuery().let { rs ->
                if (rs.next())
                    return mapper(rs)
            }
        }
        return null
    }

    companion object {
        private const val clientColumns = "id, secret"
        private const val CLIENT_INSERT = "INSERT INTO client($clientColumns) VALUES (?, ?)"
        private const val CLIENT_FIND_BY_ID = "SELECT $clientColumns FROM client WHERE id=?"

        private const val userColumns = "id, identity_provider, username, password, active, display_name, created_at"
        private const val USER_INSERT = "INSERT INTO users($userColumns) VALUES (?, ?, ?, ?, ?, ?, ?)"
        private const val USER_UPDATE_PASSWORD = "UPDATE users SET password=? WHERE id=?"
        private const val USER_UPDATE_DISPLAY_NAME = "UPDATE users SET display_name=? WHERE id=?"
        private const val USER_FIND_BY_ID = "SELECT $userColumns FROM users WHERE id=?"
        private const val USER_FIND_BY_USERNAME_AND_PROVIDER = "SELECT $userColumns FROM users WHERE username=? AND identity_provider=?"

        private const val tokenColumns =
            "user_id, client_id, token_type, access_token, access_token_expiration, refresh_token, refresh_token_expiration"
        private const val TOKEN_INSERT = "INSERT INTO token($tokenColumns) VALUES (?, ?, ?, ?, ?, ?, ?)"
        private const val TOKEN_FIND_BY_ACCESS_TOKEN = "SELECT $tokenColumns FROM token WHERE access_token=?"
        private const val TOKEN_FIND_BY_REFRESH_TOKEN = "SELECT $tokenColumns FROM token WHERE refresh_token=?"
        private const val TOKEN_DELETE = "DELETE FROM token WHERE access_token=?"
    }
}