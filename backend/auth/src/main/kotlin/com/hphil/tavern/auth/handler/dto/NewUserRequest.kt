package com.hphil.tavern.auth.handler.dto

class NewUserRequest(
    val username: String,
    var password: String,
    var displayName: String
)