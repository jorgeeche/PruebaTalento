package com.data.operations.marvel

import com.data.InMemoryDataSource
import com.domain.operations.marvel.GetCharactersDomainResponse

class MarvelDataSource : InMemoryDataSource {

    private var charactersResponse: GetCharactersDomainResponse? = null


    fun saveCharacters(charactersResponse: GetCharactersDomainResponse) {
        this.charactersResponse = charactersResponse
    }

    fun retrieveCharactersResponse(): GetCharactersDomainResponse? = this.charactersResponse


    override fun clearData() {
    }

}
