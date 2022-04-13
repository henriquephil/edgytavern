package com.hphil.tavern.resources.manager

import com.hphil.tavern.domain.Establishment
import com.hphil.tavern.repository.EstablishmentRepository
import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal
import javax.ws.rs.core.SecurityContext

interface ManagedEstablishmentTrait {

    fun getEstablishment(establishmentRepository: EstablishmentRepository, ctx: SecurityContext): Establishment {
        val user = (ctx.userPrincipal as OidcJwtCallerPrincipal).subject
        return establishmentRepository.find("managerUid", user)
                .firstResult() ?: error("Establishment does not exists")
    }

    fun validateManager(establishment: Establishment, ctx: SecurityContext) {
        val sub = (ctx.userPrincipal as OidcJwtCallerPrincipal).subject
        if (establishment.managerUid != sub) {
            error("User has no authority over this establishment");
        }
    }
}