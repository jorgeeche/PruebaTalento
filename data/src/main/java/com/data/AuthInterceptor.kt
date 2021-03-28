package com.data

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import com.data.operations.padel.PadelInMemoryDataSource
import commons.android.SystemInformation

class AuthInterceptor(
    private val padelAppInMemoryDataSource: PadelInMemoryDataSource,
    private val systemInformation: SystemInformation
) : Interceptor {

    companion object {
        private const val ACCEPT_KEY = "Accept"
        private const val ACCEPT_TYPE_ENCRYPT_PDF_KEY = "application/pdf, application/json"
        private const val ACCEPT_TYPE_JSON_KEY = "application/json"

        private const val CONNECTION_TYPE_KEY = "connectionType"
        private const val OPERATIVE_SYSTEM_KEY = "operativeSystem"
        private const val MACHINE_TYPE_KEY = "machineType"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        val response = chain.proceed(
            createRequestWithoutToken(originalRequest)
        )

        return response
    }

    private fun createRequestWithoutToken(originalRequest: Request): Request {
        return originalRequest
            .newBuilder()
            .appendAcceptHeaders(originalRequest)
            .addHeader(OPERATIVE_SYSTEM_KEY, systemInformation.getOperativeSystem())
            .addHeader(MACHINE_TYPE_KEY, "${systemInformation.getPhoneManufacter()} ${systemInformation.getPhoneModel()}")
            .method(originalRequest.method(), originalRequest.body())
            .build()
    }


    private fun Request.Builder.appendAcceptHeaders(originalRequest: Request) = with(this) {
        if (originalRequest.header("Accept") != null && originalRequest.header("Accept") == "application/pdf") {
            removeHeader(ACCEPT_KEY) //We remove this because retrofit append two headers called Accept
            addHeader(ACCEPT_KEY, ACCEPT_TYPE_ENCRYPT_PDF_KEY)
        } else {
            addHeader(ACCEPT_KEY, ACCEPT_TYPE_JSON_KEY)
        }
    }

}