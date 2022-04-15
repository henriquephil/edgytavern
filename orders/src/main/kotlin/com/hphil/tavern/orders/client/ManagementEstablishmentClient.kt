package com.hphil.client

import com.hphil.client.responses.ManagementEstablishmentResponse
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.ws.rs.GET
import javax.ws.rs.Path


@Path("/management/establishment")
@RegisterRestClient
//@ClientHeaderParam(name = "Authorization", value = ["{authorizationHeaderGenerator.authorizationHeader}"])
//@RegisterClientHeaders
//resteasy.role.based.security=true ???
interface ManagementEstablishmentClient {

    @GET
    fun getManagersEstablishment(): ManagementEstablishmentResponse

    // https://github.com/eclipse/microprofile-rest-client/blob/master/spec/src/main/asciidoc/clientexamples.asciidoc
}