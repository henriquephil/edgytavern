package com.hphil.tavern.establishment.services

import com.mashape.unirest.http.Unirest
import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.UnauthorizedResponse
import io.javalin.security.RouteRole

const val HEADER_AUTHORIZATION = "Authorization"

enum class TokenType(val text: String) {
    BEARER("bearer")
}

fun accessManager(handler: Handler, ctx: Context, permittedRoles: Set<RouteRole>) {
    val userInfo = getUserInfo(ctx)
    ctx.attribute("userInfo", userInfo)
    if (ctx.endpointHandlerPath().startsWith("admin") && userInfo !is AdminUserInfo)
        throw UnauthorizedResponse()
    handler.handle(ctx)
}


private fun getUserInfo(ctx: Context): UserInfo {
    val fullAuthHeader = ctx.header(HEADER_AUTHORIZATION) ?: return AnonymousUserInfo()
    val values = fullAuthHeader.split(" ")
    if (values.size < 2)
        error("missing token")
    if (!values[0].equals(TokenType.BEARER.text, true))
        error("invalid header")

    return Unirest.get("http://localhost:8089/auth/userInfo")
        .header(HEADER_AUTHORIZATION, fullAuthHeader)
        .asObject(AdminUserInfo::class.java)
        .body
}

interface UserInfo

class AdminUserInfo(
    val id: String,
    val username: String,
    val displayName: String,
//    val createdAt: LocalDateTime
) : UserInfo

class AnonymousUserInfo : UserInfo
