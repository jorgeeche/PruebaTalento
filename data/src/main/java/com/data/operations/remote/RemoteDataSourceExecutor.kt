package com.data.operations.remote

import com.commons.android.NetworkManager
import com.domain.RepositoryFailure
import commons.android.Try

class RemoteDataSourceExecutor(
    private val networkManager: NetworkManager
    //private val sessionManager: SessionManager
) {

    suspend operator fun <KnownError, Success> invoke(block: suspend () -> ParsedResponse<KnownError, Success>): ParsedResponse<KnownError, Success> {
        //TODO: Refrescamos sesión?
        //sessionManager.refreshSession()

        if (!networkManager.isThereConnectionToInternet()) {
            return ParsedResponse.Failure(RepositoryFailure.NoInternet)
        }

        when (val result = Try.invokeSuspend { block() }) {
            is Try.Exception -> return ParsedResponse.Failure(RepositoryFailure.Unknown)
            is Try.Success -> return result.s
        }
    }
}
