package com.hphil.tavern.auth.service.encoder

class NoopPasswordEncoder: PasswordEncoder {
    override fun matches(password: String, encoded: String): Boolean {
        return password == encoded
    }

    override fun encode(password: String): String {
        return password
    }
}
