package com.hphil.tavern.establishment.controller

import com.hphil.tavern.establishment.domain.Establishment
import com.hphil.tavern.establishment.repository.EstablishmentRepository
import org.hashids.Hashids
import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class EstablishmentConverter(
    private val establishmentRepository: EstablishmentRepository,
    private val hashids: Hashids
) : Converter<String, Establishment> {
    override fun convert(establishmentHash: String): Establishment? {
        return establishmentRepository.findByIdAndOwnerUsername(
            hashids.decode(establishmentHash)[0],
            SecurityContextHolder.getContext().authentication.name
        )
    }

}