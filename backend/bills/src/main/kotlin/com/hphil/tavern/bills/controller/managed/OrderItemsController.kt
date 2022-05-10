package com.hphil.tavern.bills.controller.managed

import com.hphil.tavern.bills.domain.OrderItemStatus
import com.hphil.tavern.bills.repository.OrderItemRepository
import com.hphil.tavern.bills.services.RegisterService
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/managed/orderItems")
class OrderItemsController(
    private val orderItemRepository: OrderItemRepository,
    private val registerService: RegisterService
) {

    @PostMapping("/{orderItemId}/shift")
    @Transactional
    fun shiftItem(@PathVariable orderItemId: Long) {
        val register = registerService.getRegisterByEstablishmentHash()
        val orderItem = orderItemRepository.findByOrderLotBillRegisterAndId(register, orderItemId)
        orderItem.status = mapOf(
            OrderItemStatus.RECEIVED to OrderItemStatus.PREPARATION,
            OrderItemStatus.PREPARATION to OrderItemStatus.DISPATCHED,
            OrderItemStatus.DISPATCHED to OrderItemStatus.DELIVERED
        )[orderItem.status] ?: error("Item already delivered")
    }

    @DeleteMapping("/{orderItemId}") // TODO maybe switch to logical deletion
    @Transactional
    fun removeItem(@PathVariable("orderItemId") orderItemId: Long) {
        val register = registerService.getRegisterByEstablishmentHash()
        val orderItem = orderItemRepository.findByOrderLotBillRegisterAndId(register, orderItemId)
        orderItemRepository.delete(orderItem)
    }

}