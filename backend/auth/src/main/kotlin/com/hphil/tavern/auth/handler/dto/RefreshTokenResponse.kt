package com.hphil.tavern.auth.handler.dto

open class RefreshTokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Long? = null,
    val refresh_token: String? = null,
    val refresh_expires_in: Long? = null
)