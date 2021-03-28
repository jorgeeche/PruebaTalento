package com.domain.operations.login

import com.commons.UseCase
import com.domain.operations.user.UserRepository
import commons.android.Either

class SwapUserLogged(private val repository: UserRepository): UseCase<Unit, Unit, Unit>() {

    override suspend fun run(params: Unit): Either<Unit, Unit> = repository.clearUserData()

}