package com.domain.operations.marvel

import commons.android.Either

interface MarvelRepository {

    suspend fun getCharacters(): Either<GetCharactersDomainFailure, GetCharactersDomainResponse>

    suspend fun getCharacterById(charId: Int): Either<GetCharactersDomainFailure, CharacterDomain>

}