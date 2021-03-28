package com.data

data class ErrorResponse(
    val error: ErrorData
)

data class ErrorData(
    val message: String,
    val code: String
)
