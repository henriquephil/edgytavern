package com.hphil.tavern.establishment.util

object MergeUtil {
    fun <O : Identifiable<K>, N : RemovalIndicator<K>, K> updateChildSet(
        original: MutableSet<O>,
        newInfoSet: Set<N>,
        caseNullCreateWith: (N) -> O,
        ifPresentUpdateWith: (O, N) -> Unit
    ) {
        newInfoSet.forEach { n ->
            val id = n.getKey()
            if (id == null) {
                original.add(caseNullCreateWith(n))
            } else {
                original.find { it.getKey() == id }?.also {
                    if (n.markedForRemoval)
                        original.remove(it)
                    else
                        ifPresentUpdateWith(it, n)
                }
            }
        }
    }
}

interface Identifiable<K> {
    fun getKey(): K?
}

interface RemovalIndicator<K> : Identifiable<K> {
    val markedForRemoval: Boolean
}