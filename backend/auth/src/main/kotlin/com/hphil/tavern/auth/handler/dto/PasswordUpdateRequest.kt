package com.hphil.tavern.auth.handler.dto

class PasswordUpdateRequest(
    val oldPassword: String,
    val newPassword: String
)