package com.hphil.tavern.establishment.util


import com.hphil.tavern.establishment.client.AuthClient
import com.hphil.tavern.establishment.client.UserInfo
import io.javalin.http.Context
import io.javalin.http.UnauthorizedResponse

const val CTX_USER_INFO = "userInfo"

fun Context.getUserInfo(): UserInfo {
    return attribute<UserInfo>(CTX_USER_INFO)
        ?: httpGetUserInfo(this)
        ?: throw UnauthorizedResponse()
}

private fun httpGetUserInfo(ctx: Context): UserInfo? {
    val userInfo = AuthClient.getUserInfo(ctx)
    ctx.attribute(CTX_USER_INFO, userInfo)
    return userInfo
}