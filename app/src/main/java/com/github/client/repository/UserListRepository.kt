package com.github.client.repository

import com.github.client.base.BaseRepository
import com.github.client.network.ApiService

class UserListRepository(private val api: ApiService) : BaseRepository() {

    fun getListUsers() = api.getListUsers()

    fun getSearchUser(username: String) = api.getSearchUsers(username)

}