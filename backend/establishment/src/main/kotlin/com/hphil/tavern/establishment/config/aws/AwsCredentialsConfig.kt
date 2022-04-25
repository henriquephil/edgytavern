package com.hphil.tavern.establishment.config.aws

import com.amazonaws.auth.BasicAWSCredentials
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AwsCredentialsConfig {
    @Bean
    fun basicAWSCredentials(awsProperties: AwsProperties): BasicAWSCredentials {
        return BasicAWSCredentials(awsProperties.accessKey, awsProperties.secretKey)
    }

}
