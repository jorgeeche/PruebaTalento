package com.data.operations.padel

class PadelPreferencesDataSource() {

    companion object {
        private const val ASSISTANCE_NUMBER_KEY = "ASSISTANCE_NUMBER"
        private const val ENC_KEY = "ENC_KEY"
    }

    fun getAssistanceNumber(): String? = ""/* = try {
        securePreferences.readString(ASSISTANCE_NUMBER_KEY)
    } catch (e: Exception) {
        null
    }*/

    /*fun saveAssistanceNumber(assistanceNumber: String): Unit = securePreferences.put(ASSISTANCE_NUMBER_KEY, assistanceNumber)


    fun getKeySecret(): String? = try {
        securePreferences.readString(ENC_KEY)
    } catch (e: java.lang.Exception) {
        null
    }

    fun saveKeySecret(secretKey: String) = securePreferences.put(ENC_KEY, secretKey)
*/
}