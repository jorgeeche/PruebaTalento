package com.data.operations.padel.contributed

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelRetrofit {

    @GET("/v1/public/characters")
    fun getCharacters(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Deferred<Response<ResponseBody>>

    @GET("/v1/public/characters/{charId}")
    fun getCharacterById(
        @Path("charId") charId: Int,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Deferred<Response<ResponseBody>>
}