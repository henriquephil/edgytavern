package com.hphil.tavern.establishment.config.aws

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("aws")
data class AwsProperties(val accessKey: String, val secretKey: String)