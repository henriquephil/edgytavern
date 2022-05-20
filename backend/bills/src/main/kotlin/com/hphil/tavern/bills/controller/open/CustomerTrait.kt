package com.hphil.tavern.bills.controller.open

import com.hphil.tavern.bills.domain.Bill
import com.hphil.tavern.bills.repository.BillRepository
import com.hphil.tavern.bills.services.security.UserInfo

interface CustomerTrait {
    fun fetchOpenBill(billRepository: BillRepository, userInfo: UserInfo): Bill =
        billRepository.findByUserUidAndOpenTrue(userInfo.id) ?: error("User has no active bill")
}