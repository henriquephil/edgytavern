package com.hphil.tavern.auth.service.encoder

interface PasswordEncoder {
    fun matches(password: String, encoded: String): Boolean
    fun encode(password: String): String
}