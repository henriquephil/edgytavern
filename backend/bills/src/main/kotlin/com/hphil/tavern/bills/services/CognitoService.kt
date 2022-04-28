package com.hphil.tavern.bills.services

import org.springframework.stereotype.Service

@Service
class CognitoService {
    fun getUserInfo(sub: String): CognitoUserInfo {
        return CognitoUserInfo(sub, "John doe")
    }
}

class CognitoUserInfo(val sub: String, val name: String)