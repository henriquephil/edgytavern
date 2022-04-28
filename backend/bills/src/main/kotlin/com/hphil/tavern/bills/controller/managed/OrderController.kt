package com.hphil.tavern.bills.controller.managed

import com.hphil.tavern.bills.client.ManagementEstablishmentClient
import com.hphil.tavern.bills.domain.OrderItemStatus
import com.hphil.tavern.bills.repository.OrderItemRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/orderItems")
class OrderController(
    private val orderItemRepository: OrderItemRepository,
    private val managementEstablishmentClient: ManagementEstablishmentClient
) {

    @PostMapping("/{orderItemId}/shift")
    @Transactional
    fun shiftItem(@PathVariable orderItemId: Long, principal: Principal) {
        val establishment = managementEstablishmentClient.getManagersEstablishment()
        val orderItem = orderItemRepository.findByEstablishmentHashAndId(establishment.hashId, orderItemId)
        orderItem.status = mapOf(
            OrderItemStatus.RECEIVED to OrderItemStatus.PREPARATION,
            OrderItemStatus.PREPARATION to OrderItemStatus.DISPATCHED,
            OrderItemStatus.DISPATCHED to OrderItemStatus.DELIVERED
        )[orderItem.status] ?: error("Item already delivered")
    }

    @DeleteMapping("/{orderItemId}")
    @Transactional
    fun removeItem(@PathVariable("orderItemId") orderItemId: Long, principal: Principal) {
        val establishment = managementEstablishmentClient.getManagersEstablishment()
        val orderItem = orderItemRepository.findByEstablishmentHashAndId(establishment.hashId, orderItemId)
        orderItemRepository.delete(orderItem)
    }
}