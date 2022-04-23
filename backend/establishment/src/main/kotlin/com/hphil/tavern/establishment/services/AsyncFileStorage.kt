package com.hphil.tavern.establishment.services

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import org.springframework.stereotype.Service
import java.io.File
import javax.imageio.ImageIO

@Service
class AsyncFileStorage(val qrCodeWriter: QRCodeWriter) {
    // TODO implement cloud storage impl

    /*TODO suspend*/ fun generateAndStoreQrCodeImage(content: String, imgName: String) {
        val qrCode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 125, 125)
        ImageIO.write(MatrixToImageWriter.toBufferedImage(qrCode), "png", File("qrcode\\$imgName.png"))
    }
}