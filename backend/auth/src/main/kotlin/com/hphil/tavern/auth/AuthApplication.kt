package com.hphil.tavern.auth

import com.hphil.tavern.auth.handler.*
import com.hphil.tavern.auth.service.AccessTokenService
import com.hphil.tavern.auth.service.AuthConfig
import com.hphil.tavern.auth.service.ClientService
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*


fun main() {
    val config = AuthConfig()

    val accessTokenService = AccessTokenService(config.repository, config.tokenConfig)
    val clientService = ClientService(config.repository)
    val tokenHandler = TokenHandler(config.repository, config.passwordEncoder, clientService, accessTokenService)
    val tokenInfoHandler = TokenInfoHandler(config.repository)
    val userInfoHandler = UserInfoHandler(config.repository)
    val googleHandler = GoogleHandler(accessTokenService, config.repository)
    val logoutHandler = LogoutHandler(config.repository)
    val userHandler = UserHandler(config.repository, accessTokenService, config.passwordEncoder)

    Javalin.create()
        .routes {
            path("/auth") {
                post("/token", tokenHandler::handle)
                post("/tokenInfo", tokenInfoHandler::handle)
                get("/userInfo", userInfoHandler::handle)
                post("/logout", logoutHandler::handle)
                post("/google", googleHandler::handle)
            }
            path("/user") {
                post("/register", userHandler::insert)
                post("/password", userHandler::updatePassword)
                post("/name", userHandler::updateDisplayName)
            }
        }
        .before { println("${it.method()} ${it.path()} ${it.body()}") }
        .start(8089)
}