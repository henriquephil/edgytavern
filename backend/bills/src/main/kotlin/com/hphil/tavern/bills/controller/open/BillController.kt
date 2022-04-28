package com.hphil.tavern.bills.controller.open

import com.hphil.tavern.bills.client.EstablishmentClient
import com.hphil.tavern.bills.domain.Bill
import com.hphil.tavern.bills.repository.BillRepository
import com.hphil.tavern.bills.repository.OrderRepository
import com.hphil.tavern.bills.services.CognitoService
import org.hashids.Hashids
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.security.Principal

/**
 * Every action available here is supposed to be accesses by the customer
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
    private val orderRepository: OrderRepository,
    private val hashids: Hashids,
    private val cognitoService: CognitoService,
    private val establishmentClient: EstablishmentClient
) : CustomerTrait {

    @PostMapping
    @Transactional
    fun openBill(@RequestBody request: OpenBillRequest, principal: Principal): OpenBillResponse {
        billRepository.findOpen(principal.name)?.also { error("There is already an active bill") }
        // fetch the spot to assert these are real hashes
        establishmentClient.getEstablishmentByHash(request.establishmentHash)
            ?: error("Establishment does not exist")
        val userInfo = cognitoService.getUserInfo(principal.name);
        val bill = billRepository.save(
            Bill(userInfo.sub, principal.name, request.establishmentHash)
        )
        return OpenBillResponse(hashids.encode(bill.id!!))
        // TODO notify
    }

    @DeleteMapping // Maybe there would be better something like POST /leave
    @Transactional
    fun cancelBill(principal: Principal) {
        val bill = fetchOpenBill(billRepository, principal)
        if (orderRepository.countByBill(bill) > 0)
            error("Bill with orders can only be closed by the manager")
        billRepository.delete(bill)
        // TODO notify
    }

}

class OpenBillRequest(val establishmentHash: String)
class OpenBillResponse(val hashId: String)
