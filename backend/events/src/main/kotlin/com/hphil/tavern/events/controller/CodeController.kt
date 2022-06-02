package com.hphil.tavern.events.controller

import com.hphil.tavern.events.service.IdentificationService
import com.hphil.tavern.events.service.security.UserInfo
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CodeController(
    private val identificationService: IdentificationService
) {

    @PostMapping("/establishment/code")
    fun getCodeForEstablishment(): CodeResponse {
        return CodeResponse(
            identificationService.getSingleUseCodeForEstablishment()
        )
    }

    @PostMapping("/customer/code")
    fun getCodeForCustomer(userInfo: UserInfo): CodeResponse {
        return CodeResponse(
            identificationService.getSingleUseCodeForCustomer(userInfo)
        )
    }
}