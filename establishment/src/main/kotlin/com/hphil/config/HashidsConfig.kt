package com.hphil.config

import org.eclipse.microprofile.config.inject.ConfigProperty
import org.hashids.Hashids
import javax.inject.Inject
import javax.inject.Singleton

class HashidsConfig {
    @Inject
    @ConfigProperty(name = "app.hash.salt", defaultValue = "salt")
    var salt: String = "salt"

    @Singleton
    fun hashids(): Hashids {
        return Hashids(salt, 6)
    }
}