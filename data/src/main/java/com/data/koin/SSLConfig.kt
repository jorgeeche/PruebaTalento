package com.data.koin

import android.content.Context
import android.util.Base64
import java.security.MessageDigest
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate


class SSLConfig(private val flavor: String, private val context: Context) {

    fun extractCertificate(): String? {
        val certificateId: Int = when (flavor) {
            //"pre" -> R.raw.padel_app_pre
            //"pro" -> R.raw.padel_app
            else -> return null
        }

        try {
            val certificate: X509Certificate = generateCertificate(certificateId)
            val publicKeyEncoded: ByteArray = certificate.publicKey.encoded
            val messageDigest: MessageDigest = MessageDigest.getInstance("SHA-256")
            val publicKeySha256 = messageDigest.digest(publicKeyEncoded)
            val publicKeyShaBase64: ByteArray = Base64.encode(publicKeySha256, Base64.DEFAULT)

            return "sha256/" + String(publicKeyShaBase64)
        } catch (e: Exception) {
            return null
        }
    }

    private fun generateCertificate(certificate: Int): X509Certificate {
        getInputStreamCertificate(certificate).use {
            return getcertificateFactory().generateCertificate(it) as X509Certificate
        }
    }

    private fun getInputStreamCertificate(certificate: Int) = context.resources.openRawResource(certificate)

    private fun getcertificateFactory() = CertificateFactory.getInstance("X.509")

}
