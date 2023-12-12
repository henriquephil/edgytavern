package com.hphil.tavern.establishment.controller.customer

import com.hphil.tavern.establishment.services.loadCustomerBill
import io.javalin.apibuilder.ApiBuilder.*

fun customerRoutes() = path("customer") {
    before {
        loadCustomerBill(it)
    }

    path("bill") {
        post(::customerOpenBill)

        get(::customerGetBill)
        delete(::customerCancelBill)
    }
    path("orders") {
        post(::customerPlaceOrder)
        get(::customerGetOrders)
    }
}