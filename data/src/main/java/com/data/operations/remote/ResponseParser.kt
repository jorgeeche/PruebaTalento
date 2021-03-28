package com.data.operations.remote

import com.commons.json.JsonParser
import com.data.ErrorResponse
import com.domain.RepositoryFailure
import commons.android.Either
import commons.android.Either.Left
import commons.android.Either.Right
import okhttp3.ResponseBody
import retrofit2.Response
import kotlin.reflect.KClass

class ResponseParser(val jsonParser: JsonParser) {

    companion object {
        const val UNAUTHORIZED_ERROR_CODE = "401"
        const val FORBIDDEN_ERROR_CODE = "403"
        const val CHARACTER_NOT_FOUND_ERROR_CODE = "404"
        const val MARVEL_ERROR_CODE = "409"
    }

    inline fun <reified KnownError : Any> parseNoContentResponse(
        response: Response<ResponseBody>,
        knownErrorKClassesByErrorCodes: Map<String, KClass<out KnownError>> = emptyMap()
    ): ParsedResponse<KnownError, NoContentResponse> {
        if (response.isSuccessful) {
            return ParsedResponse.Success(NoContentResponse())

        } else {
            when (val either = parseError(response, knownErrorKClassesByErrorCodes)) {
                is Left -> return either.l
                is Right -> return either.r
            }
        }
    }


    inline fun <reified KnownError : Any, reified Success> parse(
        response: Response<ResponseBody>,
        knownErrorKClassesByErrorCodes: Map<String, KClass<out KnownError>> = emptyMap()
    ): ParsedResponse<KnownError, Success> {
        if (response.isSuccessful) {
            return parseSuccess(response)

        } else {
            when (val either = parseError(response, knownErrorKClassesByErrorCodes)) {
                is Left -> return either.l
                is Right -> return either.r
            }
        }
    }

    inline fun <reified Success> parseSuccess(response: Response<ResponseBody>): ParsedResponse.Success<Success> {
        val successBody: String = response.body()!!.string()
        val success: Success = jsonParser.fromJson(successBody, Success::class.java)
        return ParsedResponse.Success(success)
    }

    inline fun <reified KnownError : Any> parseError(
        response: Response<ResponseBody>,
        knownErrorKClassesByErrorCodes: Map<String, KClass<out KnownError>>
    ): Either<ParsedResponse.Failure, ParsedResponse.KnownError<KnownError>> {


        val errorBody: String = response.errorBody()!!.string()
        val errorResponse: ErrorResponse = jsonParser.fromJson(errorBody, ErrorResponse::class.java)
        val errorCode: String = errorResponse.error!!.code

        if (errorCode == UNAUTHORIZED_ERROR_CODE) {
            return Left(ParsedResponse.Failure(RepositoryFailure.Unauthorized))
        }else if (errorCode == FORBIDDEN_ERROR_CODE){
            return Left(ParsedResponse.Failure(RepositoryFailure.Forbidden))
        }else if (errorCode == MARVEL_ERROR_CODE){
            return Left(ParsedResponse.Failure(RepositoryFailure.MarvelError))
        }else if (errorCode == CHARACTER_NOT_FOUND_ERROR_CODE){
            return Left(ParsedResponse.Failure(RepositoryFailure.CharacterNotFound))
        }

        val knownErrorClass: KClass<out KnownError> = knownErrorKClassesByErrorCodes[errorCode] ?: throw Exception()
        val objectInstance: KnownError? = knownErrorClass.objectInstance
        if (objectInstance != null) {
            return Right(ParsedResponse.KnownError(objectInstance))
        }

        val knownError: KnownError = jsonParser.fromJson(errorBody, knownErrorClass.java)
        return Right(ParsedResponse.KnownError(knownError))
    }
}