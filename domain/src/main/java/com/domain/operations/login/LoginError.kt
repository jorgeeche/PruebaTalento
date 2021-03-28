package com.domain.operations.login

sealed class LoginError {

    object ExceededAttempsError : LoginError()

    object InvalidCredentialsError : LoginError()

    object UserNotFoundError: LoginError()

    object UserBlockedError : LoginError()

}