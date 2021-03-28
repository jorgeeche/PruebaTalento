package com.data.operations.login

import com.data.operations.padel.contributed.UserRetrofit
import com.data.operations.remote.ParsedResponse
import com.data.operations.remote.ResponseParser
import com.domain.operations.login.LoginCredentials
import com.domain.operations.login.LoginError
import okhttp3.ResponseBody
import retrofit2.Response
import kotlin.reflect.KClass

class LoginRemoteDataSource(private val userRetrofit: UserRetrofit, private val responseParser: ResponseParser) {

    suspend fun callLogin(params: LoginCredentials): ParsedResponse<LoginError, LoginResponse> {
        val response: Response<ResponseBody> = userRetrofit.callLogin(params).await()
        return responseParser.parse(response, getKnownErrorClassesByErrorCodes())
    }

    private fun getKnownErrorClassesByErrorCodes(): Map<String, KClass<out LoginError>> = mapOf(
            "120" to LoginError.InvalidCredentialsError::class,
            "121" to LoginError.UserNotFoundError::class,
            "122" to LoginError.UserBlockedError::class
    )
}