package com.hphil.tavern.establishment.services

import com.hphil.tavern.establishment.repository.entity.BillEntity
import io.javalin.http.Context
import java.util.*

const val TAB_SESSION = "tab-session"
const val KEY_CUSTOMER_BILL = "customerBill"

fun loadCustomerBill(ctx: Context) {
    ctx.headerAsClass(TAB_SESSION, UUID::class.java).allowNullable().get()?.let {
        val bill = BillEntity.findBySessionAndOpenTrue(it) ?: error("No active bill found for tab")
        ctx.attribute(KEY_CUSTOMER_BILL, bill)
    }
}

fun Context.getCustomerBill() = attribute<BillEntity>(KEY_CUSTOMER_BILL) ?: error("Bill not found")
