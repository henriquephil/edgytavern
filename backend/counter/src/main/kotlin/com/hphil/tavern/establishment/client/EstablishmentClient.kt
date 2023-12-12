package com.hphil.tavern.establishment.client

import com.hphil.tavern.establishment.client.responses.AssetResponse
import com.hphil.tavern.establishment.client.responses.EstablishmentResponse
import com.hphil.tavern.establishment.client.responses.SpotResponse
import com.hphil.tavern.establishment.client.responses.TabResponse
import com.mashape.unirest.http.Unirest
import io.javalin.http.Context

// TODO param base url
fun getEstablishmentByHash(ctx: Context, establishmentHash: String): EstablishmentResponse? =
    Unirest.get("http://localhost:8081/${establishmentHash}")
        .headers(ctx.headerMap())
        .asObject(EstablishmentResponse::class.java)
        .body

fun getSpotByHash(ctx: Context, establishmentHash: String, spotHash: String): SpotResponse? =
    Unirest.get("http://localhost:8081/${establishmentHash}/spots/${spotHash}")
        .headers(ctx.headerMap())
        .asObject(SpotResponse::class.java)
        .body

fun getAssetByHash(ctx: Context, establishmentHash: String, assetHash: String): AssetResponse? =
    Unirest.get("http://localhost:8081/${establishmentHash}/assets/${assetHash}")
        .headers(ctx.headerMap())
        .asObject(AssetResponse::class.java)
        .body

fun getTabByCode(ctx: Context, establishmentHash: String, tabCode: String): TabResponse? =
    Unirest.get("http://localhost:8081/${establishmentHash}/tabs/${tabCode}")
        .headers(ctx.headerMap())
        .asObject(TabResponse::class.java)
        .body

fun getTabByCode(establishmentHash: String, tabCode: String): TabResponse? =
    Unirest.get("http://localhost:8081/${establishmentHash}/tabs/${tabCode}")
        .asObject(TabResponse::class.java)
        .body

