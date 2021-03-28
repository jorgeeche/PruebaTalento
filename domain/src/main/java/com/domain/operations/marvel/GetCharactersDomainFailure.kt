package com.domain.operations.marvel

import com.domain.RepositoryFailure

sealed class GetCharactersDomainFailure {
    data class Repository(val error: RepositoryFailure) : GetCharactersDomainFailure()
}