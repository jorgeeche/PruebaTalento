package com.data.operations.user

import com.data.operations.prefs.SecurePreferences
import commons.android.Try

class UserPreferencesDataSource(private val securePreferences: SecurePreferences) {

    companion object {
        private const val USER_LOGGED = "USER_LOGGED"
        private const val ALIAS_KEY = "ALIAS"
        private const val PWD_KEY = "PWD_KEY"
        private const val NAME_KEY = "NAME_KEY"
        private const val ROLE_KEY = "ROLE_KEY"
    }

    fun getAlias(): Try<String?> = Try { securePreferences.readString(ALIAS_KEY) }
    fun saveAlias(alias: String): Try<Unit> = Try { securePreferences.put(ALIAS_KEY, alias) }

    fun getName(): Try<String?> = Try { securePreferences.readString(NAME_KEY) }
    fun saveName(shortName: String): Try<Unit> = Try { securePreferences.put(NAME_KEY, shortName) }

    fun getRole(): Try<String?> = Try { securePreferences.readString(ROLE_KEY) }
    fun saveRole(shortName: String): Try<Unit> = Try { securePreferences.put(ROLE_KEY, shortName) }

    fun getUserLoggedStatus(): Try<Boolean?> = Try { securePreferences.read(USER_LOGGED, Boolean::class.java) }
    fun updateUserLoggedStatus(status: Boolean) = Try { securePreferences.put(USER_LOGGED, status) }

    fun getPwd(): Try<String?> = Try { securePreferences.readString(PWD_KEY) }
    fun savePwd(pwd: String) = Try { securePreferences.put(PWD_KEY, pwd) }

    fun clearData(): Try<Unit> = Try {
        securePreferences.removeKey(ALIAS_KEY)
        securePreferences.removeKey(PWD_KEY)
        securePreferences.removeKey(NAME_KEY)
        securePreferences.removeKey(USER_LOGGED)
    }

}