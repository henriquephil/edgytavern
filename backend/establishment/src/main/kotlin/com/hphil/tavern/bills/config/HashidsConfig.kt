package com.hphil.tavern.bills.config

import org.hashids.Hashids
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HashidsConfig {
    @Value("\${app.hash.salt}")
    var salt: String = "salt"

    @Bean
    fun hashids(): Hashids {
        return Hashids(salt, 6)
    }
}