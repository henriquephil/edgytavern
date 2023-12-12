package com.hphil.tavern.establishment.controller.admin.active

import com.hphil.tavern.establishment.repository.entity.BillEntity
import com.hphil.tavern.establishment.repository.entity.OrderItemEntity
import com.hphil.tavern.establishment.services.BillService
import com.hphil.tavern.establishment.services.getCurrentRegister
import com.hphil.tavern.establishment.utils.getContextAppAttribute
import io.javalin.http.Context
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Every action available here are supposed to be called by the manager
 *
 * The manager can:
 * . Read all the active bills, orders and order items in his establishment
 * . Read a specific bill, detailed
 * . Read a specific OrderItem, detailed
 * . Progress the ordered item through the operation
 * . Remove an order item
 * . Close the bill
 *
 */
fun adminOpenBill(ctx: Context) {
    val tabCode = ""
    val billService = ctx.getContextAppAttribute(BillService::class)
    transaction {
        val register = ctx.getCurrentRegister()
        billService.openBill(register, tabCode)
    }
}

fun adminCloseBill(ctx: Context) {
    val billId: Long = ctx.pathParamAsClass("billId", Long::class.java).get()
    transaction {
        val register = ctx.getCurrentRegister()
        val bill = BillEntity.findById(register, billId)
        assert(OrderItemEntity.countNotDeliveredByBill(bill) == 0L) { "Can't close bill with open orders" }
        bill.close()
    }
}

fun releaseBillSession(ctx: Context) {
    val billId: Long = ctx.pathParamAsClass("billId", Long::class.java).get()
    transaction {
        val register = ctx.getCurrentRegister()
        val bill = BillEntity.findById(register, billId)
        bill.releaseSession()
    }
}

fun getActiveRegisterBills(ctx: Context) {
    transaction {
        val register = ctx.getCurrentRegister()
        val bills = BillEntity.findAllByRegister(register)
        ctx.json(bills.map { bill ->
            BillDto(
                bill.id.value,
                bill.tabCode,
                bill.open,
                bill.started.atZone(ZoneId.systemDefault()),
                bill.ended?.atZone(ZoneId.systemDefault())
            )
        })
    }
}

private class BillDto(val id: Long, val tabCode: String, val open: Boolean, val started: ZonedDateTime, val ended: ZonedDateTime?)
