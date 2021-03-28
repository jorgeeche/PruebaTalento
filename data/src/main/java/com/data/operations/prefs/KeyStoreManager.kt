package com.data.operations.prefs

import android.annotation.TargetApi
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.*
import java.security.spec.RSAKeyGenParameterSpec
import java.security.spec.RSAKeyGenParameterSpec.F4
import java.util.*
import javax.crypto.Cipher
import javax.security.auth.x500.X500Principal

class KeyStoreManager {

    companion object {
        const val KEYSTORE_PROVIDER_ANDROID_KEYSTORE = "AndroidKeyStore"
        const val TYPE_RSA = "RSA"
        const val PADDING_TYPE = "PKCS1Padding"
        const val BLOCKING_MODE = "NONE"
    }

    /**
     * Creates a public and private key and stores it using the Android Key
     * Store, so that only this application will be able to access the keys.
     */
    fun createKeys(context: Context, alias: String) {
        if (!isSigningKey(alias)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                createKeysM(alias, false)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                createKeysJBMR2(context, alias)
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun createKeysJBMR2(context: Context, alias: String) {
        val start = GregorianCalendar()
        val end = GregorianCalendar()
        end.add(Calendar.YEAR, 30)

        val spec = KeyPairGeneratorSpec.Builder(context)
            .setAlias(alias)    // You'll use the alias later to retrieve the key. It's a key for the key!
            .setSubject(X500Principal("CN=$alias"))
            .setSerialNumber(BigInteger.valueOf(Math.abs(alias.hashCode().toLong())))
            .setStartDate(start.time).setEndDate(end.time)  // Date range of validity for the generated pair.
            .build()

        val kpGenerator = KeyPairGenerator.getInstance(TYPE_RSA, KEYSTORE_PROVIDER_ANDROID_KEYSTORE)
        kpGenerator.initialize(spec)
        val kp = kpGenerator.generateKeyPair()
        Log.d(TAG, "Public Key is: " + kp.public.toString())
    }


    @TargetApi(Build.VERSION_CODES.M)
    fun createKeysM(alias: String, requireAuth: Boolean) {
        try {
            val keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, KEYSTORE_PROVIDER_ANDROID_KEYSTORE)
            keyPairGenerator.initialize(
                KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setAlgorithmParameterSpec(RSAKeyGenParameterSpec(1024, F4))
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                    .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA384, KeyProperties.DIGEST_SHA512)
                    .setUserAuthenticationRequired(requireAuth) // Only permit the private key to be used if the user authenticated within the last five minutes.
                    .build()
            )
            val keyPair = keyPairGenerator.generateKeyPair()
            Log.d(TAG, "Public Key is: " + keyPair.public.toString())

        } catch (e: NoSuchProviderException) {
            throw RuntimeException(e)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: InvalidAlgorithmParameterException) {
            throw RuntimeException(e)
        }
    }

    /**
     * JBMR2+ If Key with the default alias exists, returns true, else false.
     * on pre-JBMR2 returns true always.
     */
    private fun isSigningKey(alias: String): Boolean {
        return try {
            val keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER_ANDROID_KEYSTORE)
            keyStore.load(null)
            keyStore.containsAlias(alias)
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            false
        }
    }

    private fun getPrivateKey(alias: String): PrivateKey? {
        return try {
            val ks = KeyStore.getInstance(KEYSTORE_PROVIDER_ANDROID_KEYSTORE)
            ks.load(null)
            ks.getKey(alias, null) as PrivateKey
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            null
        }
    }

    private fun getPublicKey(alias: String): PublicKey? {
        return try {
            val ks = KeyStore.getInstance(KEYSTORE_PROVIDER_ANDROID_KEYSTORE)
            ks.load(null)
            ks.getCertificate(alias).publicKey
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            null
        }
    }

    fun encrypt(alias: String, plaintext: String): String {
        try {
            val cipher = getCipher()
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(alias))
            return Base64.encodeToString(cipher.doFinal(plaintext.toByteArray()), Base64.NO_WRAP)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun decrypt(alias: String, ciphertext: String): String {
        return try {
            val cipher = getCipher()
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(alias))
            String(cipher.doFinal(Base64.decode(ciphertext, Base64.NO_WRAP)))

        } catch (e: Exception) {
            ""
        }
    }


    fun encryptKey(key: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        digest.update(key.byteInputStream(Charset.forName("UTF-8")).readBytes())

        return Base64.encodeToString(digest.digest(), Base64.DEFAULT)
    }

    private fun getCipher(): Cipher {
        return Cipher.getInstance(
            String.format(
                "%s/%s/%s",
                TYPE_RSA,
                BLOCKING_MODE,
                PADDING_TYPE
            )
        )
    }

}
