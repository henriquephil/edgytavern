package com.hphil.tavern.bills.services

import org.springframework.stereotype.Service

@Service
class CognitoService {
    fun getUserInfo(sub: String): CognitoUserInfo {
        return CognitoUserInfo("", "")
    }
}

class CognitoUserInfo(val sub: String, val name: String)