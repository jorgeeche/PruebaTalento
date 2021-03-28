package com.domain.operations.marvel

import com.commons.UseCase
import commons.android.Either

class GetCharacterById(private val repository: MarvelRepository) : UseCase<GetCharactersDomainFailure, CharacterDomain, Int>() {

    override suspend fun run(charId: Int): Either<GetCharactersDomainFailure, CharacterDomain> = repository.getCharacterById(charId)

}