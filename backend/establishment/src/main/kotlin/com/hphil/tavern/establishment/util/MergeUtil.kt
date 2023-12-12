package com.hphil.tavern.establishment.util

import org.jetbrains.exposed.dao.Entity

fun <O : Entity<K>, N : UpdatableRequestEntity<K>, K> List<O>.updateWith(
    requestList: List<N>,
    inserter: (N) -> O,
    ifPresentUpdateWith: (O, N) -> Unit
) {
    requestList.forEach { n ->
        val id = n.getKey()
        if (id == null) {
            inserter(n)
        } else {
            find { it.id.value == id }?.also {
                if (n.markedForRemoval)
                    it.delete()
                else
                    ifPresentUpdateWith(it, n)
            }
        }
    }
}

interface UpdatableRequestEntity<K> {
    val markedForRemoval: Boolean
    fun getKey(): K?
}