package com.hphil.tavern.auth

import com.hphil.tavern.auth.handler.*
import com.hphil.tavern.auth.service.AuthConfig
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*


fun main() {
    val config = AuthConfig()


    val tokenHandler = TokenHandler(config.repository, config.passwordEncoder, config.tokenConfig)
    val tokenInfoHandler = TokenInfoHandler(config.repository)
    val userInfoHandler = UserInfoHandler(config.repository)
    val logoutHandler = LogoutHandler(config.repository)
    val userHandler = UserHandler(config.repository, config.passwordEncoder)

//    config.repository.addClient(Client("client", "secret"))
//    config.repository.addUser(User(
//        username = "user",
//        password = config.passwordEncoder.encode("password"),
//        displayName = "User"
//    ))

    Javalin.create()
        .routes {
            path("/auth") {
                post("/token", tokenHandler::handle)
                post("/tokenInfo", tokenInfoHandler::handle)
                get("/userInfo", userInfoHandler::handle)
                post("/logout", logoutHandler::handle)
            }
            path("/user") {
                post("/register", userHandler::insert)
                post("/password", userHandler::updatePassword)
            }
        }
        .before { println("${it.method()} ${it.path()}") }
        .start(8089)
}