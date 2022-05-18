package com.hphil.tavern.auth.utils

import com.hphil.tavern.auth.model.TokenType
import com.hphil.tavern.auth.service.Constants
import io.javalin.http.Context

object TokenExtractor {
    fun extract(context: Context): String {
        val fullAuthHeader = context.header(Constants.HEADER_AUTHORIZATION) ?: error("user not authenticated")
        val values = fullAuthHeader.split(" ")
        if (values.size < 2)
            error("missing header")
        if (!values[0].equals(TokenType.BEARER.text, true))
            error("invalid header")
        return values[1]
    }
}