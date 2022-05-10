package com.hphil.tavern.bills

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
@EnableFeignClients
@ConfigurationPropertiesScan
@EnableKafka
class EstablishmentApplication

fun main(args: Array<String>) {
	runApplication<EstablishmentApplication>(*args)
}
