package com.hphil.tavern.bills.config

import com.hphil.tavern.bills.controller.managed.EstablishmentLoader
import com.hphil.tavern.bills.repository.EstablishmentRepository
import com.hphil.tavern.bills.services.UserInfoResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(val establishmentRepository: EstablishmentRepository): WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(EstablishmentLoader(establishmentRepository))
        resolvers.add(UserInfoResolver())
    }
}