package com.hphil.tavern.bills.config

/**
 * See DefaultAccessTokenConverter.extractAuthentication()
 * I could override the DefaultUserAuthenticationConverter (aiming to override extractAuthentication())
 *
 * Could also implement an UserDetailsService to load by username
 * But for now, we will be using its username as index
 */
//class CognitoAccessTokenConverter : JwtAccessTokenConverter() {
//
//    override fun extractAuthentication(claims: MutableMap<String, *>): OAuth2Authentication? {
//        (claims as MutableMap<String?, Any?>)[UserAuthenticationConverter.USERNAME] = (claims["username"] as String)
//        return super.extractAuthentication(claims)
//    }
//}