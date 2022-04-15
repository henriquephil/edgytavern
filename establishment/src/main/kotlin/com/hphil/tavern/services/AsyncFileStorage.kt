package com.hphil.tavern.services

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import java.io.File
import javax.enterprise.context.ApplicationScoped
import javax.imageio.ImageIO
import javax.inject.Inject
import javax.transaction.Transactional

@ApplicationScoped
class AsyncFileStorage {
    // TODO implement cloud storage impl

    @Inject
    lateinit var qrCodeWriter: QRCodeWriter

    // TODO async
//    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    fun generateAndStoreQrCodeImage(content: String, imgName: String) {
        val qrCode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 125, 125)
        ImageIO.write(MatrixToImageWriter.toBufferedImage(qrCode), "png", File(imgName + ".png"))
    }
}