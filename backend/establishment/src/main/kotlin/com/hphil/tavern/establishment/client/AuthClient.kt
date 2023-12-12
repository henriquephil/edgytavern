package com.hphil.tavern.establishment.client

import com.mashape.unirest.http.Unirest
import io.javalin.http.Context

object AuthClient {
    fun getUserInfo(ctx: Context): UserInfo? = Unirest.get("http://localhost:8089/auth/userInfo")
        .headers(ctx.headerMap())
        .asObject(UserInfo::class.java)
        .body
}

class UserInfo(
    val id: String,
    val username: String,
    val active: Boolean,
    var displayName: String?
)
