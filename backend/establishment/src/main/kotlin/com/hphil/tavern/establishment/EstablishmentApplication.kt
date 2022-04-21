package com.hphil.tavern.establishment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
class EstablishmentApplication

fun main(args: Array<String>) {
	runApplication<EstablishmentApplication>(*args)
}
