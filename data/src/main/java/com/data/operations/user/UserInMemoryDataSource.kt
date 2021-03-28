package com.data.operations.user

import com.data.InMemoryDataSource

class UserInMemoryDataSource : InMemoryDataSource {

    private var isFirstLogin: Boolean = true

    fun updateIsFirstLogin(isFirstLogin: Boolean) {
        this.isFirstLogin = isFirstLogin
    }

    fun isFirstLogin() = isFirstLogin

    override fun clearData() {
    }

}