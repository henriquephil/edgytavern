package com.hphil.tavern.events

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.http.MediaType
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
class EventListeners(
    private val objectMapper: ObjectMapper
) {
    // TODO there is A LOT to improve here

    val emitters = ConcurrentHashMap<String, MutableList<SseEmitter>>() // get rid of this

    @KafkaListener(
        topics = ["order-received", "bill-opened"],
        groupId = "group-id"
    )
    fun establishmentListener(payload: ConsumerRecord<String, String>) {
        val obj = objectMapper.readTree(payload.value())
        val establishmentHash = obj.get("establishmentHash").asText()
        emitters[establishmentHash]?.forEach { // and get rid of this
            try {
                it.send(
                    SseEmitter.event()
                        .id(UUID.randomUUID().toString()) // TODO improve
                        .name(payload.topic())
                        .data(obj, MediaType.APPLICATION_JSON)
                )
            } catch (e: Exception) {
                dropEmitter(establishmentHash, it, e)
                // TODO metric
                e.printStackTrace()
            }
        }
    }

    fun registerEmitter(s: String, emitter: SseEmitter) {
        val list = emitters.getOrPut(s) { mutableListOf() }
        synchronized(list) {
            list.add(emitter)
        }
        emitter.send("CONNECTED") // is that a good idea?
    }

    fun dropEmitter(establishmentHash: String, emitter: SseEmitter, ex: Exception) {
        emitters[establishmentHash]?.also {
            synchronized(it) { // is it safe?
                it.remove(emitter)
            }
        }
        synchronized(emitter) {
            emitter.completeWithError(ex)
        }
    }
}