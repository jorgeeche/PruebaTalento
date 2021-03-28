package com.domain.operations.login

import com.domain.RepositoryFailure

sealed class CallLoginFailure {

    data class Repository(val error: RepositoryFailure) : CallLoginFailure()

    data class Known(val error: LoginError) : CallLoginFailure()

}