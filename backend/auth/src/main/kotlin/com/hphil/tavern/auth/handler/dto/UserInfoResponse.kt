package com.hphil.tavern.auth.handler.dto

import com.hphil.tavern.auth.model.User

class UserInfoResponse(
    val id: String,
    val username: String,
    val active: Boolean,
    var displayName: String?
) {
    constructor(user: User) : this(
        user.id,
        user.username,
        user.active,
        user.displayName
    )
}