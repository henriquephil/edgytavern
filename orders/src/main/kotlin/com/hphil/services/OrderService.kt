package com.hphil.services

import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class OrderService {

    /**
     * Add an order, with 1 or more items, to the bill.
     * The Bill must be active
     */
    fun addOrder() {

    }

    /**
     * Move the Requested item to the next status,
     * be it from received to separation, preparation, dispatched or delivered
     */
    fun carryOrderItem() { // shiftOrderItem?

    }

    /**
     * Completely remove an Order Item from the Bill
     */
    fun removeOrderItem() {

    }

}