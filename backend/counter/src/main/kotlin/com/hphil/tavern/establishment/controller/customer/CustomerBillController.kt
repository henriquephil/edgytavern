package com.hphil.tavern.establishment.controller.customer

import com.hphil.tavern.establishment.repository.entity.OrderLotEntity
import com.hphil.tavern.establishment.services.BillService
import com.hphil.tavern.establishment.services.getCustomerBill
import com.hphil.tavern.establishment.utils.getContextAppAttribute
import io.javalin.http.Context
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

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
fun customerOpenBill(ctx: Context) {
    val request: OpenBillRequest = ctx.bodyAsClass(OpenBillRequest::class.java)
    val billService: BillService = ctx.getContextAppAttribute(BillService::class)
    val bill = billService.openBill(request.establishmentHash, request.tabCode)
    ctx.json(OpenBillResponse(bill.session.toString()))
}

fun customerGetBill(ctx: Context) {
    transaction {
        ctx.getCustomerBill().let {
            ctx.json(BillResponse(it.session!!, it.tabCode, it.started.atZone(ZoneId.systemDefault())))
        }
    }
}

fun customerCancelBill(ctx: Context) {
    transaction {
        val bill = ctx.getCustomerBill()
        if (OrderLotEntity.countByBill(bill) > 0)
            error("Bill with orders can only be closed by the manager")
        bill.delete()
    }
}

private class OpenBillRequest(val establishmentHash: String, val tabCode: String)
private class OpenBillResponse(val session: String)
private class BillResponse(val session: UUID, val tabCode: String, val started: ZonedDateTime)