package com.hphil.tavern.bills.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
open class Register(
    open val establishmentHash: String
) {
    @Id
    @GeneratedValue
    open val id: Long? = null
    open var open: Boolean = true
    open val started: LocalDateTime = LocalDateTime.now()
    open var ended: LocalDateTime? = null

    fun close() {
        open = false
        ended = LocalDateTime.now()
    }
}