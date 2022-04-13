package com.hphil.resources.management

import com.hphil.domain.Establishment
import com.hphil.repository.EstablishmentRepository
import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal
import javax.ws.rs.core.SecurityContext

interface ManagerEstablishmentTrait {

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