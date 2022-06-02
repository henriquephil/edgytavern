package com.hphil.tavern.events.service

import com.hphil.tavern.events.client.ManagedEstablishmentClient
import com.hphil.tavern.events.service.security.UserInfo
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
class IdentificationService(
    private val managedEstablishmentClient: ManagedEstablishmentClient
) {
    // TODO include request origin in key
    private val establishmentCache = ConcurrentHashMap<UUID, String/*establishment.hashId*/>() // TODO cache service
    private val customerCache = ConcurrentHashMap<UUID, String/*user.id*/>() // TODO cache service

    fun getSingleUseCodeForEstablishment(): UUID {
        val establishment = managedEstablishmentClient.getManagedEstablishment()
        val single = UUID.randomUUID()
        establishmentCache[single] = establishment.hashId
        return single
    }

    fun popEstablishmentHash(code: UUID): String {
        return establishmentCache.remove(code) ?: error("not a valid code")
    }

    fun getSingleUseCodeForCustomer(userInfo: UserInfo): UUID {
        val single = UUID.randomUUID()
        customerCache[single] = userInfo.id
        return single
    }

    fun popCustomerHash(code: UUID): String {
        return customerCache.remove(code) ?: error("not a valid code")
    }
}