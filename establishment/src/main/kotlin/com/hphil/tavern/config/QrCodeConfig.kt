package com.hphil.tavern.config

import com.google.zxing.qrcode.QRCodeWriter
import javax.inject.Singleton

class QrCodeConfig {
    @Singleton
    fun qrCodeWriter(): QRCodeWriter = QRCodeWriter()
}