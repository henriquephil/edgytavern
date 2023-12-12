package com.hphil.tavern.establishment.services

import com.hphil.tavern.establishment.client.getTabByCode
import com.hphil.tavern.establishment.repository.entity.BillEntity
import com.hphil.tavern.establishment.repository.entity.RegisterEntity

class BillService(
    private val queuePublisher: QueuePublisher
) {

    fun openBill(establishmentHash: String, tabCode: String): BillEntity {
        val register = RegisterEntity.findByEstablishmentHashAndOpenTrue(establishmentHash) ?: error("Establishment not open")
        return openBill(register, tabCode)
    }

    fun openBill(register: RegisterEntity, tabCode: String): BillEntity {
        val bill = BillEntity.findByTabCodeAndOpenTrue(register, tabCode) ?: create(register, tabCode)
        bill.beginSession()
        return bill
    }

    private fun create(register: RegisterEntity, tabCode: String): BillEntity {
        val tab = getTabByCode(register.establishmentHash, tabCode) ?: error("invalid tab code")
        val bill = BillEntity.new(tab.code, register)
        queuePublisher.billOpened(bill)
        return bill
    }
}