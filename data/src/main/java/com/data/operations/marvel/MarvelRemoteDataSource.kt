package com.data.operations.marvel

import com.data.operations.padel.contributed.MarvelRetrofit
import com.data.operations.remote.ParsedResponse
import com.data.operations.remote.ResponseParser
import okhttp3.ResponseBody
import org.koin.android.BuildConfig
import retrofit2.Response

class MarvelRemoteDataSource(private val marvelRetrofit: MarvelRetrofit, private val responseParser: ResponseParser) {

    suspend fun getCharacters(): ParsedResponse<Unit, GetCharactersResponse> {
        val response: Response<ResponseBody> = marvelRetrofit.getCharacters("1",com.data.BuildConfig.KEY,com.data.BuildConfig.HASH).await()
        return responseParser.parse(response)
    }

    suspend fun getCharacterById(charId: Int): ParsedResponse<Unit, GetCharactersResponse> {
        val response: Response<ResponseBody> = marvelRetrofit.getCharacterById(charId,"1",com.data.BuildConfig.KEY,com.data.BuildConfig.HASH).await()
        return responseParser.parse(response)
    }

}