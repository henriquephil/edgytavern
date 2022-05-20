package com.hphil.tavern.auth.service

import com.hphil.tavern.auth.service.encoder.BcryptPasswordEncoder
import com.hphil.tavern.auth.service.encoder.PasswordEncoder
import com.hphil.tavern.auth.service.repository.JdbcRepository
import com.hphil.tavern.auth.service.repository.Repository
import java.util.*

open class AuthConfig(
    val repository: Repository = JdbcRepository(),
    val passwordEncoder: PasswordEncoder = BcryptPasswordEncoder(),
    val tokenConfig: TokenConfig = TokenConfig()
)

open class TokenConfig(
    val accessTokenTtl: Long = 15 * 60,
    val refreshTokenTtl: Long? = null,
    val generateToken: () -> String = { UUID.randomUUID().toString() }
)