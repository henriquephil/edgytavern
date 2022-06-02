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
class EstablishmentEventListeners(
    private val identificationService: IdentificationService,
    private val objectMapper: ObjectMapper
) {
    private val emitters = ConcurrentHashMap<String, MutableList<SseEmitter>>()

    @KafkaListener(
        topics = ["order-received", "bill-opened"],
        groupId = "group-id" // TODO instance group id
    )
    fun establishmentListener(payload: ConsumerRecord<String, String>) {
        val obj = objectMapper.readTree(payload.value())
        val establishmentHash = obj.get("establishmentHash").asText()
        emitters[establishmentHash]?.forEach {
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

    fun registerEmitter(code: UUID, emitter: SseEmitter) {
        val establishmentHash = identificationService.popEstablishmentHash(code)
        val list = emitters.getOrPut(establishmentHash) { mutableListOf() }
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