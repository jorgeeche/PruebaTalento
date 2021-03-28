package securedata

import android.content.Context

interface SecureData {
    companion object {
        fun newInstance(context: Context, text: String): SecureData = SecureDataImpl(
            context,
            text
        )
    }


    fun encrypt(plainText: String): String
    fun decrypt(cipherText: String): String
}