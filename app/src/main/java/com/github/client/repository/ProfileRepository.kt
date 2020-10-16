package com.github.client.repository

import com.github.client.base.BaseRepository
import com.github.client.model.UserProfile
import com.github.client.model.UserReposItem
import com.github.client.network.ApiService
import io.reactivex.Single

class ProfileRepository(private val apiService: ApiService) : BaseRepository() {

    fun getUser(userName: String): Single<UserProfile> = apiService.getUser(userName)

    fun getUserRepository(userName: String): Single<List<UserReposItem>> =
        apiService.getUserRepos(userName)

}