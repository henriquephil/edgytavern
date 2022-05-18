package com.hphil.tavern.bills.config

import org.hashids.Hashids
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HashidsConfig {
    @Value("\${app.hash.salt}")
    var salt: String = "salt"

    // get rid of this stupidity :
    // https://instagram-engineering.com/sharding-ids-at-instagram-1cf5a71e5a5c

    @Bean
    fun hashids(): Hashids {
        return Hashids(salt, 6)
    }
}