package com.data.operations.marvel

import com.data.operations.remote.ParsedResponse
import com.data.operations.remote.RemoteDataSourceExecutor
import com.domain.RepositoryFailure
import com.domain.operations.marvel.*
import commons.android.Either

class MarvelRepositoryImpl(
    private val remoteDataSourceExecutor: RemoteDataSourceExecutor,
    private val marvelDataSource: MarvelDataSource,
    private val marvelRemoteDataSource: MarvelRemoteDataSource
) : MarvelRepository {

    override suspend fun getCharacters(): Either<GetCharactersDomainFailure, GetCharactersDomainResponse> {
        val getCharactersDomainResponse: GetCharactersDomainResponse? = marvelDataSource.retrieveCharactersResponse()
        if (getCharactersDomainResponse!=null) {
            return Either.Right(getCharactersDomainResponse)
        }

        val parsedResponse: ParsedResponse<Unit, GetCharactersResponse> = remoteDataSourceExecutor { marvelRemoteDataSource.getCharacters() }

        return when (parsedResponse) {
            is ParsedResponse.Success -> {
                marvelDataSource.clearData()
                marvelDataSource.saveCharacters(parsedResponse.success.toDomain())
                Either.Right(parsedResponse.success.toDomain())
            }
            is ParsedResponse.KnownError -> Either.Left(
                GetCharactersDomainFailure.Repository(
                    RepositoryFailure.Unknown
                )
            )
            is ParsedResponse.Failure -> Either.Left(GetCharactersDomainFailure.Repository(parsedResponse.failure))
        }
    }

    override suspend fun getCharacterById(charId: Int): Either<GetCharactersDomainFailure, CharacterDomain> {
        val getCharactersDomainResponse: GetCharactersDomainResponse? = marvelDataSource.retrieveCharactersResponse()

        if (getCharactersDomainResponse!=null) {
            return Either.Right(getCharacter(charId, getCharactersDomainResponse))
        }

        val parsedResponse: ParsedResponse<Unit, GetCharactersResponse> = remoteDataSourceExecutor { marvelRemoteDataSource.getCharacterById(charId) }

        return when (parsedResponse) {
            is ParsedResponse.Success -> {
                marvelDataSource.clearData()
                marvelDataSource.saveCharacters(parsedResponse.success.toDomain())
                Either.Right(getCharacter(charId, parsedResponse.success.toDomain()))
            }
            is ParsedResponse.KnownError -> Either.Left(
                GetCharactersDomainFailure.Repository(
                    RepositoryFailure.Unknown
                )
            )
            is ParsedResponse.Failure -> Either.Left(GetCharactersDomainFailure.Repository(parsedResponse.failure))
        }
    }

    private fun getCharacter(charId: Int, charactersDomainResponse: GetCharactersDomainResponse): CharacterDomain{
        var aux = 0
        charactersDomainResponse.data!!.results!!.map { it ->
        if (it.id!!.equals(charId))
            return charactersDomainResponse.data!!.results!!.get(aux)
        else aux++
        }
        return charactersDomainResponse.data!!.results!!.get(0)
    }

}