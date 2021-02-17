package com.hphil.util

object MergeUtil {
    fun <O, N : RemovalIndicator, K> updateChildSet(original: MutableSet<O>, originalKey: (O) -> K,
                                                    nSet: Set<N>, nKey: (N) -> K?,
                                                    create: (N) -> O,
                                                    update: (O, N) -> Unit) {
        nSet.forEach { n ->
            val id = nKey(n)
            if (id == null) {
                original.add(create(n))
            } else {
                val o = original.find { originalKey(it) == id } ?: error("Error identifying original value for $id")
                if (n.markedForRemoval)
                    original.remove(o)
                else
                    update(o, n)
            }
        }
    }

    interface RemovalIndicator {
        val markedForRemoval: Boolean
    }
}