package com.hphil.tavern.util

import io.quarkus.panache.common.Parameters

object PanacheQueryFilter {
    fun where(vararg filter: Pair<String, Any?>): Pair<String, Parameters> {
        val params = Parameters()
        val cons = mutableListOf<String>()
        filter.forEach { (attr, value) ->
            if (value != null) {
                params.and(attr, value)
                cons.add("$attr = :${attr}")
            }
        }
        return Pair(cons.joinToString(" and "), params)
    }
}