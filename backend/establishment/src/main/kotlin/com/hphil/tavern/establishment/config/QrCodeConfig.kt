package com.hphil.tavern.establishment.config

import com.google.zxing.qrcode.QRCodeWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QrCodeConfig {
    @Bean
    fun qrCodeWriter(): QRCodeWriter = QRCodeWriter()
}