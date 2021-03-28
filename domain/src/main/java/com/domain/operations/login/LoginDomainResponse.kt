package com.domain.operations.login

data class LoginDomainResponse(
    val token: String,
    val user: UserDomainLogin

)

data class UserDomainLogin(
    val email: String,
    val name: String,
    val is_first_login: Boolean,
    val email_confirmed: Boolean,
    val role: String,
    val profile_image_url : String
)