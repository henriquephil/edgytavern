package com.hphil.tavern.bills.controller.open

import com.hphil.tavern.bills.domain.Bill
import com.hphil.tavern.bills.repository.BillRepository
import java.security.Principal

interface CustomerTrait {
    fun fetchOpenBill(billRepository: BillRepository, principal: Principal): Bill =
        billRepository.findOpen(principal.name) ?: error("User has no active bill")
}