package com.hphil.tavern.bills.config

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableResourceServer
class ResourceServerConfig(
    private val resource: ResourceServerProperties
) : ResourceServerConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.cors().configurationSource(corsConfigurationSource())
        http.csrf().disable()
        http.authorizeRequests()
            .antMatchers("/**").permitAll()
            .antMatchers("/actuator/health").permitAll()
            .anyRequest().authenticated()
    }

    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    // Enabling Cognito Converter
//    @Bean
//    fun jwkTokenStore(): TokenStore {
//        return JwkTokenStore(
//            Collections.singletonList(resource.jwk.keySetUri),
//            CognitoAccessTokenConverter(),
//            null
//        )
//    }
}