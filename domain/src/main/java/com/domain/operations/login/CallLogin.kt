package com.domain.operations.login

import com.commons.UseCase
import com.domain.operations.user.UserRepository
import commons.android.Either

class CallLogin(private val repository: UserRepository) : UseCase<CallLoginFailure, LoginDomainResponse, LoginCredentials>() {

    override suspend fun run(params: LoginCredentials): Either<CallLoginFailure, LoginDomainResponse> = repository.callUserLogin(params)

}