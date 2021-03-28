package securedata

import android.content.Context

class SecureDataImpl(
    private val context: Context,
    private val text: String
) :SecureData {

    override fun encrypt(plainText: String): String {
        TODO("Not yet implemented")
    }

    override fun decrypt(cipherText: String): String {
        TODO("Not yet implemented")
    }

}