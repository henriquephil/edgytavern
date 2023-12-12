package com.hphil.tavern.establishment.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.hphil.tavern.establishment.repository.entity.BillEntity
import com.hphil.tavern.establishment.repository.entity.OrderLotEntity
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.time.LocalDateTime


class QueuePublisher(
    private val objectMapper: ObjectMapper = ObjectMapper()
) {
    private val producer: KafkaProducer<String, String>

    init {
        val producerProps = mapOf(
            "bootstrap.servers" to "localhost:9092",
            "key.serializer" to "org.apache.kafka.common.serialization.StringSerializer",
            "value.serializer" to "org.apache.kafka.common.serialization.ByteArraySerializer",
            "security.protocol" to "PLAINTEXT"
//          "client.id" = InetAddress.getLocalHost().hostName
//          "acks" = "all"
        )
        producer = KafkaProducer<String, String>(producerProps)
    }

    fun orderReceived(order: OrderLotEntity) {
        publish("order-received", OrderMessage(order)) // maybe just the establishmentHashId
    }

    fun billOpened(bill: BillEntity) {
        publish("bill-opened", BillOpenedMessage(bill)) // maybe just the establishmentHashId
    }

    private fun publish(topic: String, payload: Any) {
        val data = objectMapper.writeValueAsString(payload)
        producer.send(
            ProducerRecord(topic, data)
        )
    }
}

class BillOpenedMessage(val establishmentHash: String, val billId: Long, val started: LocalDateTime, val tabCode: String) {
    constructor(bill: BillEntity) : this(bill.register.establishmentHash, bill.id.value, bill.started, bill.tabCode)
}

class OrderMessage(val establishmentHash: String, val orderId: Long, val billId: Long, val createdAt: LocalDateTime, val spotHashId: String) {
    constructor(order: OrderLotEntity) : this(order.bill.register.establishmentHash, order.id.value, order.bill.id.value, order.createdAt, order.spot.hashId)
}
