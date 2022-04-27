package com.hphil.tavern.establishment.controller.managed

import com.hphil.tavern.establishment.domain.Establishment
import com.hphil.tavern.establishment.repository.EstablishmentRepository
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class EstablishmentLoader(
    private val establishmentRepository: EstablishmentRepository
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return Establishment::class.java.isAssignableFrom(parameter.parameterType)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        return establishmentRepository.findByOwnerUsername(SecurityContextHolder.getContext().authentication.name)
    }


}