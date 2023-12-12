package com.hphil.tavern.establishment.client

import com.hphil.tavern.establishment.client.responses.ManagedEstablishmentResponse
import com.mashape.unirest.http.Unirest
import io.javalin.http.Context

// TODO param base url
const val baseUrl = "http://localhost:8081"

fun getManagedEstablishment(ctx: Context): ManagedEstablishmentResponse? = Unirest.get("${baseUrl}/managed")
    .headers(ctx.headerMap())
    .asObject(ManagedEstablishmentResponse::class.java)
    .body

