package com.domain

sealed class RepositoryFailure {

    object NoInternet : RepositoryFailure()

    object Unauthorized : RepositoryFailure()

    object Forbidden : RepositoryFailure()

    object MarvelError : RepositoryFailure()

    object CharacterNotFound : RepositoryFailure()

    object Unknown : RepositoryFailure()



}