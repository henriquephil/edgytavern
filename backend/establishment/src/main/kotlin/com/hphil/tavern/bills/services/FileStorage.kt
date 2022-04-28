package com.hphil.tavern.bills.services

import com.amazonaws.services.s3.AmazonS3
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import com.hphil.tavern.bills.config.aws.AwsS3Config
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO


@Service
class FileStorage(val s3: AmazonS3, val qrCodeWriter: QRCodeWriter) {

    fun generateAndStoreQrCodeImage(content: String, imgName: String) {
        val qrCode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 125, 125)
        val os = ByteArrayOutputStream()
        ImageIO.write(MatrixToImageWriter.toBufferedImage(qrCode), "png", os)
        val byteArray = os.toByteArray()
        s3.putObject(AwsS3Config.BUCKET_QR_CODE, "$imgName.png", ByteArrayInputStream(byteArray), null)
    }
}