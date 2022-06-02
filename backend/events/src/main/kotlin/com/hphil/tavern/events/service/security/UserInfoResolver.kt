package com.hphil.tavern.events.service.security

import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class UserInfoResolver: HandlerMethodArgumentResolver  {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return UserInfo::class.java.isAssignableFrom(parameter.parameterType)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): UserInfo {
        return (SecurityContextHolder.getContext().authentication as OAuth2Authentication).userAuthentication.principal as UserInfo
    }
}