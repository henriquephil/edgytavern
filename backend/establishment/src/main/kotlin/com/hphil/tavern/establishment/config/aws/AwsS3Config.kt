package com.hphil.tavern.establishment.config.aws

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean

class AwsS3Config {
    @Bean
    fun s3(credentials: AWSCredentials): AmazonS3 = AmazonS3ClientBuilder
        .standard()
        .withCredentials(AWSStaticCredentialsProvider(credentials))
        .withRegion(Regions.US_EAST_1)
        .build()
}