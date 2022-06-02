package com.hphil.tavern.bills.controller.managed

import com.hphil.tavern.bills.domain.OrderItem
import com.hphil.tavern.bills.domain.OrderLot
import com.hphil.tavern.bills.domain.references.AssetReference
import com.hphil.tavern.bills.repository.OrderItemRepository
import com.hphil.tavern.bills.repository.OrderLotRepository
import com.hphil.tavern.bills.services.RegisterService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.DateTimeFormat.ISO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.ZoneId
import java.time.ZonedDateTime

@RestController
@RequestMapping("/managed/recent-orders")
class RecentOrdersController(
    private val orderLotRepository: OrderLotRepository,
    private val orderItemRepository: OrderItemRepository,
    private val registerService: RegisterService
) {
    @GetMapping
    fun getRecentOrders(@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) since: ZonedDateTime?): RecentOrdersResponse {
        val register = registerService.getRegisterByEstablishmentHash()
        val orderItems = when (since) {
            null -> orderLotRepository.findAllByBillRegister(register)
            else -> orderLotRepository.findAllByBillRegisterAndCreatedAtGreaterThan(register, since.toLocalDateTime())
        }
        return RecentOrdersResponse(
            orderItems.flatMap { lot ->
                orderItemRepository.findAllByOrderLot(lot).map { RecentOrdersDto(lot, it) }
            }
        )
    }
}

class RecentOrdersResponse(val items: List<RecentOrdersDto>) {
    @DateTimeFormat(iso = ISO.DATE_TIME) val timestamp: ZonedDateTime = ZonedDateTime.now()
}

class RecentOrdersDto(
    val id: Long,
    val asset: AssetDto,
    val quantity: Int,
    val status: String,
    val spotName: String,
    val spotNumber: Int,
    val createdAt: ZonedDateTime,
    val name: String
) {
    constructor(lot: OrderLot, item: OrderItem) : this(
        item.id!!,
        AssetDto(item.asset),
        item.quantity,
        item.status.name,
        lot.spot.name,
        lot.spot.number,
        lot.createdAt.atZone(ZoneId.systemDefault()),
        lot.bill.userName
    )
}

class AssetDto(
    val name: String,
    val additionals: List<String>,
    val removed: List<String>
) {
    constructor(asset: AssetReference) : this(
        asset.name,
        asset.requestedAdditionals.map { it.name },
        asset.removedIngredients.map { it.name }
    )
}