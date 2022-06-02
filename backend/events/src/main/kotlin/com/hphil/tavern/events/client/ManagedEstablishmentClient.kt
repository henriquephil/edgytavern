package com.hphil.tavern.events.client

import com.hphil.tavern.events.client.responses.ManagedEstablishmentResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "establishment-managed", url = "\${app.clients.establishment.url}")
interface ManagedEstablishmentClient {
    @GetMapping("/managed")
    fun getManagedEstablishment(): ManagedEstablishmentResponse
}
