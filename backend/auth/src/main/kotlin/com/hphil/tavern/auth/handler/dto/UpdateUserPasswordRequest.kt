package com.hphil.tavern.auth.handler.dto

class UpdateUserPasswordRequest(
    val oldPassword: String,
    val newPassword: String
)