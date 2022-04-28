package com.hphil.tavern.bills.client

import com.hphil.tavern.bills.client.responses.AssetResponse
import com.hphil.tavern.bills.client.responses.EstablishmentResponse
import com.hphil.tavern.bills.client.responses.SpotResponse

interface EstablishmentClient {
    fun getEstablishmentByHash(establishmentHash: String): EstablishmentResponse?
    fun getSpotByHash(establishmentHash: String, spotHash: String): SpotResponse?
    fun getAssetByHash(establishmentHash: String, assetHashId: String): AssetResponse?
}