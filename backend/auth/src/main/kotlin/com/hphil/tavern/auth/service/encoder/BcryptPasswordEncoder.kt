package com.hphil.tavern.auth.service.encoder

import at.favre.lib.crypto.bcrypt.BCrypt

class BcryptPasswordEncoder: PasswordEncoder {
    override fun matches(password: String, encoded: String): Boolean {
        return BCrypt.verifyer().verify(password.toByteArray(), encoded.toByteArray()).verified
    }

    override fun encode(password: String): String {
        return BCrypt.withDefaults().hashToString(8, password.toCharArray())
    }
}
