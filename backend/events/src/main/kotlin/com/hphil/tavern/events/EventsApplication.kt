package com.hphil.tavern.events

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class EventsApplication

fun main(args: Array<String>) {
	runApplication<EventsApplication>(*args)
}
