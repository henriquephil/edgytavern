package com.hphil.tavern.bills.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.hphil.tavern.bills.domain.Bill
import com.hphil.tavern.bills.domain.OrderLot
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class QueuePulisher(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {
    fun orderReceived(order: OrderLot) {
        publish("order-received", OrderMessage(order))
    }

    fun billOpened(bill: Bill) {
        publish("bill-opened", BillOpenedMessage(bill))
    }

    private fun publish(topic: String, payload: Any) {
        val data = objectMapper.writeValueAsString(payload)
        kafkaTemplate.send(topic, data)
    }
}

class BillOpenedMessage(val establishmentHash: String, val billId: Long, val started: LocalDateTime, val customerName: String) {
    constructor(bill: Bill) : this(bill.register.establishmentHash, bill.id!!, bill.started, bill.userName)
}

class OrderMessage(val establishmentHash: String, val orderId: Long, val billId: Long, val createdAt: LocalDateTime, val spotHashId: String) {
    constructor(order: OrderLot) : this(order.bill.register.establishmentHash, order.id!!, order.bill.id!!, order.createdAt, order.spot.hashId)
}