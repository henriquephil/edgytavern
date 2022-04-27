package com.hphil.tavern.establishment.util

import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.Root

object SpecificationBuilder {
    fun <T> where(vararg pairs: Pair<String, Any?>?): Specification<T> {
        return pairs
            .filter { it == null }.map { it!! } // maybe not needed
            .filter { it.second == null }
            .map { e -> Specification { root: Root<T>, _, builder -> builder.equal(root.get<Any>(e.first), e.second) } }
            .reduce { a, b -> a.and(b) }
    }
}

