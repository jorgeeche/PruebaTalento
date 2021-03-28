package com.data.operations.padel

import com.data.InMemoryDataSource

class PadelInMemoryDataSource : InMemoryDataSource {
    private var token: String? = null
    private var userId: String? = null

    fun saveUserId(cmfId:String){
        this.userId = cmfId
    }

    fun retrieveUserId() : String? = userId

    fun saveToken(token: String) {
        this.token = token
    }

    fun retrieveToken(): String? = token

    override fun clearData() {
        TODO("Not yet implemented")
    }
}