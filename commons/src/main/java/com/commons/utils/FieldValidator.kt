package com.commons.utils

import commons.android.Either
import java.util.regex.Pattern

class FieldValidator {

    fun validateAliasLogin(alias: String) = alias.isNotBlank() && !alias.contains("*")
    fun validatePasswordLogin(password: String) = password.isNotBlank()
    fun validateLocationEmpty(city: String) = city.isNotBlank()

    fun validateEmail(email: String): Boolean {
        val expression = "^([\\w-_+%]+)(\\.[\\w-_+%]+)*@(\\w[\\w-_+%]*)(\\.[\\w-_+%]+)*(\\.[\\w-_+%]{1,5}[\\w-_+%]+)\$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        return pattern.matcher(email).matches()
    }

    fun validateAlias(alias: String) = alias.isNotBlank() && alias.length >= 6 && !alias.contains("*")

    fun validateSameValues(first : String, second : String) = first.equals(second)

    fun validateAliasRegister(alias: String) : Either<AliasError, Unit> {
        return when {
            alias.isBlank() || alias.length < 6 -> Either.Left(AliasError.LOWER_THAN_SIX_CHARACTERS)
            hasSpecialCharacters(alias) -> Either.Left(AliasError.HAS_SPECIAL_CHARACTERS)
            else -> Either.Right(Unit)
        }
    }

    fun hasSpecialCharacters(string: String) : Boolean {
        val expression = "^([A-Za-z0-9_@\\.])*$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        return !pattern.matcher(string).matches()
    }

    fun validatePassword(password: String): Either<PasswordError, Unit> {
        return when {
            password.isBlank() -> Either.Left(PasswordError.LOWER_THAN_SIX_CHARACTERS)
            password.length < 6 -> Either.Left(PasswordError.LOWER_THAN_SIX_CHARACTERS)
            !password.contains("\\d".toRegex()) -> Either.Left(PasswordError.LETTER_AND_NUMBER)
            !password.contains("[a-zA-Z]".toRegex()) -> Either.Left(PasswordError.LETTER_AND_NUMBER)
            hasCharactersDuplicated(password) -> Either.Left(PasswordError.CHARACTER_REPEATED)
            else -> Either.Right(Unit)
        }
    }


    private fun hasCharactersDuplicated(string: String): Boolean {
        var isDuplicated = false
        string.forEachIndexed { position, currentValue ->
            if (!isDuplicated) {
                val secondValue: Char
                val thirdValue: Char

                if (position + 2 <= string.length - 1) {
                    thirdValue = string[position + 2]
                    secondValue = string[position + 1]

                    isDuplicated = currentValue == secondValue && currentValue == thirdValue
                }
            }
        }

        return isDuplicated
    }

}