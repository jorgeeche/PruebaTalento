package com.domain.operations.marvel

import com.commons.UseCase
import commons.android.Either

class GetCharacters(private val repository: MarvelRepository) : UseCase<GetCharactersDomainFailure, GetCharactersDomainResponse, Unit>() {

    override suspend fun run(params: Unit): Either<GetCharactersDomainFailure, GetCharactersDomainResponse> = repository.getCharacters()

}