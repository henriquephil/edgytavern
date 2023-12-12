package com.hphil.tavern.establishment.enums

enum class OrderItemStatus(val next: OrderItemStatus) {
    DELIVERED(error("Item already delivered")),
    DISPATCHED(DELIVERED),
    PREPARATION(DISPATCHED),
    RECEIVED(PREPARATION),

    CANCELED(error("Item canceled"))
}
