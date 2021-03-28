package com.domain.operations.user

import com.domain.operations.login.CallLoginFailure
import com.domain.operations.login.LoginCredentials
import com.domain.operations.login.LoginDomainResponse
import commons.android.Either

interface UserRepository {

    suspend fun callUserLogin(params: LoginCredentials) : Either<CallLoginFailure, LoginDomainResponse>

    suspend fun clearAppData(): Either<Unit, Unit>

    suspend fun clearUserData(): Either<Unit, Unit>

}