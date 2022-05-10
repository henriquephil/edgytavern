package com.hphil.tavern.bills.controller.managed

import com.hphil.tavern.bills.repository.BillRepository
import com.hphil.tavern.bills.repository.OrderItemRepository
import com.hphil.tavern.bills.services.RegisterService
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Every action available here are supposed to be called by the manager
 *
 * The manager can:
 * . Read all of the active bills, orders and order items in his establishment
 * . Read an specific bill, detailed
 * . Read an specific OrderItem, detailed
 * . Progress the ordered item trough the operation
 * . Remove an order item
 * . Close the bill
 *
 */
@RestController
@RequestMapping("/managed/bills")
class BillsController(
    private val billRepository: BillRepository,
    private val orderItemRepository: OrderItemRepository,
    private val registerService: RegisterService
) {

    @GetMapping
    fun getBills(@RequestParam(required = false) registerId: Long?): ManagerBillsResponse {
        val register = registerService.getRegisterByEstablishmentHash(registerId)
        return ManagerBillsResponse(billRepository.findAllByRegister(register)
            .map { bill ->
                BillDto(
                    bill.id!!,
                    bill.userName,
                    bill.open,
                    bill.started
                )
            })
    }

    @GetMapping("/{id}")
    fun getBill(@PathVariable id: Long): ManagerBillResponse {
        val register = registerService.getRegisterByEstablishmentHash()
        val bill = billRepository.findByRegisterAndId(register, id)
        return ManagerBillResponse(
            bill.id!!,
            bill.userName,
            bill.open,
            bill.started,
            bill.ended,
            bill.orderLots.map { order ->
                OrderDto(
                    order.spot.name,
                    order.createdAt,
                    orderItemRepository.findAllByOrderLot(order).map { item ->
                        OrderItemDto(
                            item.id!!,
                            item.status.toString(),
                            item.asset.name,
                            item.asset.category.name,
                            item.asset.removedIngredients.map { it.name },
                            item.asset.requestedAdditionals.map { it.name },
                            item.asset.finalPrice,
                            item.quantity,
                            item.finalValue
                        )
                    }
                )
            }
        )
    }

    @PostMapping("/{billId}/close")
    @Transactional
    fun closeBill(@PathVariable billId: Long) {
        val register = registerService.getRegisterByEstablishmentHash()
        val bill = billRepository.findByRegisterAndId(register, billId)
        // verification needed
        bill.close()
    }
}

class ManagerBillsResponse(val bills: List<BillDto>)
class BillDto(val id: Long, val customerName: String, val open: Boolean, val started: LocalDateTime)

class ManagerBillResponse(
    val id: Long,
    val customerName: String,
    var open: Boolean,
    val started: LocalDateTime,
    var ended: LocalDateTime?,
    val orders: List<OrderDto>
) {
    val total = orders.flatMap { it.items }.map { it.totalPrice }.fold(BigDecimal.ZERO, BigDecimal::add)
}
class OrderDto(
    val spotName: String,
    val createdAt: LocalDateTime,
    val items: List<OrderItemDto>
)
class OrderItemDto(
    val id: Long,
    val status: String,
    val assetName: String,
    val assetCategoryName: String,
    val removedIngredients: List<String>,
    val requestedAdditionals: List<String>,
    val unitPrice: BigDecimal,
    val quantity: Int,
    val totalPrice: BigDecimal
)




