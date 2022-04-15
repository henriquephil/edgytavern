package com.hphil.tavern.util

object MergeUtil {
    fun <O : Identifiable<K>, N : RemovalIndicator<K>, K> updateChildSet(original: MutableSet<O>,
                                                                         nSet: Set<N>,
                                                                         create: (N) -> O,
                                                                         update: (O, N) -> Unit) {
        nSet.forEach { n ->
            val id = n.getKey()
            if (id == null) {
                original.add(create(n))
            } else {
                val o = original.find { it.getKey() == id } ?: error("Error identifying original value for $id")
                if (n.markedForRemoval)
                    original.remove(o)
                else
                    update(o, n)
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