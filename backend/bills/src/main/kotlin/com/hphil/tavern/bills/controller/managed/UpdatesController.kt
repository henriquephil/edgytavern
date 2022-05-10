package com.hphil.tavern.bills.controller.managed

import com.hphil.tavern.bills.repository.BillRepository
import com.hphil.tavern.bills.repository.OrderLotRepository
import com.hphil.tavern.bills.services.RegisterService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/managed/updates")
class UpdatesController(
    private val billRepository: BillRepository,
    private val orderLotRepository: OrderLotRepository,
    private val registerService: RegisterService
) {

    @GetMapping
    fun getNewOrders(@RequestParam(required = false) lastUpdate: LocalDateTime?): List<OrderLotDto> {
        val register = registerService.getRegisterByEstablishmentHash()
        val orderLots =
            if (lastUpdate == null) {
                orderLotRepository.findAllByBillRegister(register)
            } else {
                orderLotRepository.findAllByBillRegisterAndCreatedAtGreaterThan(register, lastUpdate)
            }
        val bills =
            if (lastUpdate == null) {
                billRepository.findAllByRegister(register)
            } else {
                billRepository.findAllByRegisterAndStartedGreaterThanOrEndedGreaterThan(register, lastUpdate, lastUpdate)
            }
        return orderLots.map {
            OrderLotDto(it.id!!, it.bill.id!!, it.spot.hashId)
        }
    }
}

class OrderLotDto(val id: Long, val billId: Long, val spotHashId: String)