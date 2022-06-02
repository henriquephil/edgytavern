package com.hphil.tavern.events.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.http.MediaType
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
class CustomerEventListeners(
    private val identificationService: IdentificationService,
    private val objectMapper: ObjectMapper
) {
    private val emitters = ConcurrentHashMap<String, MutableList<SseEmitter>>()

    @KafkaListener(
        topics = ["order-item-movement"],
        groupId = "group-id" // TODO instance group id
    )
    fun establishmentListener(payload: ConsumerRecord<String, String>) {
        val obj = objectMapper.readTree(payload.value())
        val userId = obj.get("userId").asText()
        emitters[userId]?.forEach {
            try {
                it.send(
                    SseEmitter.event()
                        .id(UUID.randomUUID().toString()) // TODO improve
                        .name(payload.topic())
                        .data(obj, MediaType.APPLICATION_JSON)
                )
            } catch (e: Exception) {
                dropEmitter(userId, it, e)
                // TODO metric
                e.printStackTrace()
            }
        }
    }

    fun registerEmitter(code: UUID, emitter: SseEmitter) {
        val userId = identificationService.popCustomerHash(code)
        val list = emitters.getOrPut(userId) { mutableListOf() }
        synchronized(list) {
            list.add(emitter)
        }
        emitter.send("CONNECTED") // is that a good idea?
    }

    fun dropEmitter(userId: String, emitter: SseEmitter, ex: Exception) {
        emitters[userId]?.also {
            synchronized(it) { // is it safe?
                it.remove(emitter)
            }
        }
        synchronized(emitter) {
            emitter.completeWithError(ex)
        }
    }
}