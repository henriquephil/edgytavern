package com.hphil.tavern.events

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Controller
class EventController(
    private val eventListeners: EventListeners
) {

    // TODO safer user identification
    @GetMapping("/establishment")
    fun sse(@RequestParam hashId: String): SseEmitter {
        val emitter = SseEmitter(Long.MAX_VALUE)
        eventListeners.registerEmitter(hashId, emitter)
        return emitter
    }
}