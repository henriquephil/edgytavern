package com.hphil.tavern.establishment.config

import com.hphil.tavern.establishment.controller.managed.EstablishmentLoader
import com.hphil.tavern.establishment.repository.EstablishmentRepository
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(val establishmentRepository: EstablishmentRepository): WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(EstablishmentLoader(establishmentRepository))
    }
}