package com.github.client.repository

import com.github.client.base.BaseRepository
import com.github.client.model.UserListItem
import com.github.client.network.ApiService
import io.reactivex.Single

class UserListRepository(private val api: ApiService) : BaseRepository() {

    fun getListUsers(): Single<List<UserListItem>> = api.getListUsers()

}