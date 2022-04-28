package com.hphil.tavern.bills

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class EstablishmentApplication

fun main(args: Array<String>) {
	runApplication<EstablishmentApplication>(*args)
}
