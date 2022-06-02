package com.hphil.tavern.events.controller

import com.hphil.tavern.events.service.CustomerEventListeners
import com.hphil.tavern.events.service.EstablishmentEventListeners
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.*

@Controller
class EventController(
    private val establishmentEventListeners: EstablishmentEventListeners,
    private val customerEventListeners: CustomerEventListeners,
) {

    @GetMapping("/establishment")
    fun establishment(@RequestParam code: UUID): SseEmitter {
        val emitter = SseEmitter(Long.MAX_VALUE)
        establishmentEventListeners.registerEmitter(code, emitter)
        return emitter
    }

    @GetMapping("/customer")
    fun customer(@RequestParam code: UUID): SseEmitter {
        val emitter = SseEmitter(Long.MAX_VALUE)
        customerEventListeners.registerEmitter(code, emitter)
        return emitter
    }
}