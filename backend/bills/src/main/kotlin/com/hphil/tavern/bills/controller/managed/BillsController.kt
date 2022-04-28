package com.hphil.tavern.bills.controller.managed

import com.hphil.tavern.bills.client.ManagedEstablishmentClient
import com.hphil.tavern.bills.repository.BillRepository
import com.hphil.tavern.bills.repository.OrderItemRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.security.Principal
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
@RequestMapping("/manager")
class BillsController(
    private val billRepository: BillRepository,
    private val orderItemRepository: OrderItemRepository,
    private val managedEstablishmentClient: ManagedEstablishmentClient
) {

    @GetMapping
    fun getBills(principal: Principal): ManagerBillsResponse {
        val establishment = managedEstablishmentClient.getManagedEstablishment()
        return ManagerBillsResponse(billRepository.openBillsAt(establishment.hashId)
            .map { bill ->
                BillDto(
                    bill.id!!,
                    bill.userName,
                    bill.started
                )
            })
    }

    @GetMapping("/{id}")
    fun getBill(@PathVariable id: Long, principal: Principal): ManagerBillResponse {
        val establishment = managedEstablishmentClient.getManagedEstablishment()
        val bill = billRepository.findByEstablishmentHashAndId(establishment.hashId, id)
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
                            item.totalPrice
                        )
                    }
                )
            }
        )
    }

    @PostMapping("/{billId}/close")
    @Transactional
    fun closeBill(@PathVariable billId: Long, principal: Principal) {
        val establishment = managedEstablishmentClient.getManagedEstablishment()
        val bill = billRepository.findByEstablishmentHashAndId(establishment.hashId, billId)
        bill.close()
    }
}

class ManagerBillsResponse(val bills: List<BillDto>)
class BillDto(val id: Long, val customerName: String, val started: LocalDateTime)

class ManagerBillResponse(
    val id: Long,
    val customerName: String,
    var open: Boolean,
    val started: LocalDateTime,
    var ended: LocalDateTime?,
    val orders: List<OrderDto>
)
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




