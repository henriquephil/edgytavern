package com.hphil.tavern.establishment.services

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import org.springframework.stereotype.Service
import java.io.PipedInputStream
import java.io.PipedOutputStream


@Service
class AsyncFileStorage(val s3: AmazonS3, val qrCodeWriter: QRCodeWriter) {

    fun generateAndStoreQrCodeImage(content: String, imgName: String) {
        val qrCode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 125, 125)
        if (!s3.doesBucketExistV2(BUCKET_QR_CODE))
            s3.createBucket(BUCKET_QR_CODE)

        val pos = PipedOutputStream()
        val pis = PipedInputStream(pos)
        MatrixToImageWriter.writeToStream(qrCode, "png", pos)

        s3.putObject(BUCKET_QR_CODE, "$imgName.png", pis, ObjectMetadata())
    }

    companion object {
        const val BUCKET_QR_CODE = "qrcode"
    }
}