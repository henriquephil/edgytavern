package com.hphil.tavern.bills.client

import com.hphil.tavern.bills.client.responses.AssetResponse
import com.hphil.tavern.bills.client.responses.EstablishmentResponse
import com.hphil.tavern.bills.client.responses.SpotResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "establishment", url = "\${app.clients.establishment.url}")
interface EstablishmentClient {
    @GetMapping("/{establishmentHash}")
    fun getEstablishmentByHash(@PathVariable establishmentHash: String): EstablishmentResponse?
    @GetMapping("/{establishmentHash}/spots/{spotHash}")
    fun getSpotByHash(@PathVariable establishmentHash: String, @PathVariable spotHash: String): SpotResponse?
    @GetMapping("/{establishmentHash}/assets/{assetHash}")
    fun getAssetByHash(@PathVariable establishmentHash: String, @PathVariable assetHash: String): AssetResponse?
}