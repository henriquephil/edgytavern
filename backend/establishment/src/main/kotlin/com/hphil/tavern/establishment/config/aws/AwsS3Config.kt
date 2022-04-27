package com.hphil.tavern.establishment.config.aws

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AwsS3Config {
    @Bean
    fun s3(credentials: AWSCredentials): AmazonS3 {
        val s3 = AmazonS3ClientBuilder
            .standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials))
//            .withRegion(Regions.US_EAST_1)
            .build()
//        if (!s3.doesBucketExistV2(BUCKET_QR_CODE))
//            s3.createBucket(BUCKET_QR_CODE)
        return s3
    }

    companion object {
        const val BUCKET_QR_CODE = "tavern-qrcodes"
    }
}