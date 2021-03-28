package com.data.operations.padel.contributed

import com.domain.operations.login.LoginCredentials
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserRetrofit {

    @POST("api/v1/login")
    fun callLogin(@Body loginParams: LoginCredentials): Deferred<Response<ResponseBody>>
}