package com.hphil.tavern.auth.model

import java.time.LocalDateTime
import java.util.*

data class User(
    val id: String = UUID.randomUUID().toString(),
    val username: String,
    var password: String,
    var active: Boolean = true,
    var displayName: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
