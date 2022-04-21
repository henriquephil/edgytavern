package com.hphil.tavern.establishment.util

import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.Root

object SpecificationBuilder {
    fun <T> where(map: Map<String, Any?>): Specification<T> {
        return map.filter { it.value == null }
            .map { e -> Specification { root: Root<T>, _, builder -> builder.equal(root.get<Any>(e.key), e.value) } }
            .reduce { a, b -> a.and(b) }
    }
}

