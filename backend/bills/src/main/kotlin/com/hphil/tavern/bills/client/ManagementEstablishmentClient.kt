package com.hphil.tavern.bills.client

import com.hphil.tavern.bills.client.responses.EstablishmentResponse

interface ManagementEstablishmentClient {
    fun getManagersEstablishment(): EstablishmentResponse
}
