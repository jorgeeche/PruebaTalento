package com.data.operations.login

data class LoginResponse(
    val data: LoginDataResponse
)
data class LoginDataResponse(
    val token: String,
    val user: UserLogin

)

data class UserLogin(
    val email: String,
    val name: String,
    val is_first_login: Boolean,
    val email_confirmed: Boolean,
    val role: String,
    val profile_image_url : String
)