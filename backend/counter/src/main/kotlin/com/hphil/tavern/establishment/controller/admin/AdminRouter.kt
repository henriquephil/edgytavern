package com.hphil.tavern.establishment.controller.admin

import com.hphil.tavern.establishment.controller.admin.active.*
import com.hphil.tavern.establishment.services.loadActiveRegister
import com.hphil.tavern.establishment.services.loadAdminEstablishment
import io.javalin.apibuilder.ApiBuilder.*

fun adminRoutes() = path("admin") {
    before { ctx ->
        loadAdminEstablishment(ctx)
    }

    path("bills/<billId>") {
        get(::adminGetBill)
    }
    path("registers") {
        get(::getAllRegistersClosed)
        path("<registerId>") {
            get("bills", ::getRegisterBills)
        }
    }
    path("active-register") {
        before {
            loadActiveRegister(it)
        }

        post(::openRegister)
        get(::getOpenRegister)

        post("close", ::closeRegister)
        get("bills", ::getActiveRegisterBills)
        get("updated-order-item-list", ::getActiveRegisterOrderItemList)
        path("bills") {
            post(::adminOpenBill)
            path("<billId>") {
                post("close", ::adminCloseBill)
                post("release", ::releaseBillSession)
            }
        }
        path("order-item/<orderItemId>") {
            post("shift", ::shiftOrderItem)
            post("cancel", ::cancelOrderItem)
        }
    }

}
