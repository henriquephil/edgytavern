package com.hphil.client

import com.hphil.client.responses.AssetResponse
import com.hphil.client.responses.EstablishmentManagerResponse
import com.hphil.client.responses.EstablishmentResponse
import com.hphil.client.responses.SpotResponse
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam


@Path("/establishment")
@RegisterRestClient
interface EstablishmentClient {

    @GET
    @Path("/{establishmentHash}")
    fun getEstablishmentByHash(@PathParam("establishmentHash") establishmentHash: String): EstablishmentResponse?

    @GET
    @Path("/{establishmentHash}/manager")
    fun getEstablishmentByHashManager(@PathParam("establishmentHash") establishmentHash: String): EstablishmentManagerResponse?

    @GET
    @Path("/{establishmentHash}/spots/{spotHash}")
    fun getSpotByHash(@PathParam("establishmentHash") establishmentHash: String, @PathParam("spotHash") spotHash: String): SpotResponse?

    @GET
    @Path("/{establishmentHash}/assets/{assetHash}")
    fun getAssetByHash(@PathParam("establishmentHash") establishmentHash: String, @PathParam("assetHash") assetHash: String): AssetResponse?

}