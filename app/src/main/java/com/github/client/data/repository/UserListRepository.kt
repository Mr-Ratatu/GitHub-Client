package com.github.client.data.repository

import com.github.client.data.network.ApiService

class UserListRepository(private val api: ApiService) {

    fun getListUsers() = api.getListUsers()

    fun getSearchUser(username: String) = api.getSearchUsers(username)

}