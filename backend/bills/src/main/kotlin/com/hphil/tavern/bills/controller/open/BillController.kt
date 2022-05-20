package com.hphil.tavern.bills.controller.open

import com.hphil.tavern.bills.domain.Bill
import com.hphil.tavern.bills.repository.BillRepository
import com.hphil.tavern.bills.repository.OrderLotRepository
import com.hphil.tavern.bills.repository.RegisterRepository
import com.hphil.tavern.bills.services.CognitoService
import com.hphil.tavern.bills.services.QueuePublisher
import com.hphil.tavern.bills.services.security.UserInfo
import org.hashids.Hashids
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

/**
 * Every action available here is supposed to be accessed by the customer
 *
 * The first thing needed by the customer is to start a new Bill at some establishment
 * He will be able to do that with the QRCode link containing the hashes for his location
 *
 * With an open Bill, he can start browsing the catalog/menu and adding orders to some spot
 * The customer can cancel his Bill, if no order has been ordered
 *
 * He will be able de read only his active bill
 *
 */
@RestController
@RequestMapping
class BillController(
    private val billRepository: BillRepository,
    private val orderLotRepository: OrderLotRepository,
    private val hashids: Hashids,
    private val cognitoService: CognitoService,
    private val registerRepository: RegisterRepository,
    private val queuePublisher: QueuePublisher
) : CustomerTrait {

    @PostMapping
    @Transactional
    fun findOrOpenBill(@RequestBody request: OpenBillRequest, userInfo: UserInfo): OpenBillResponse {
        val bill = billRepository.findByUserUidAndOpenTrue(userInfo.id) ?: create(request, userInfo)
        return OpenBillResponse(hashids.encode(bill.id!!))
    }

    private fun create(request: OpenBillRequest, userInfo: UserInfo): Bill {
        val register = registerRepository.findByEstablishmentHashAndOpenTrue(request.establishmentHash) ?: error("Register not open")
        val bill = billRepository.save(
            Bill(userInfo.id, userInfo.displayName, register)
        )
        queuePublisher.billOpened(bill)
        return bill;
    }

    @DeleteMapping
    @Transactional
    fun cancelBill(userInfo: UserInfo) {
        val bill = fetchOpenBill(billRepository, userInfo)
        if (orderLotRepository.countByBill(bill) > 0)
            error("Bill with orders can only be closed by the manager")
        billRepository.delete(bill)
    }
}

class OpenBillRequest(val establishmentHash: String)
class OpenBillResponse(val hashId: String)
