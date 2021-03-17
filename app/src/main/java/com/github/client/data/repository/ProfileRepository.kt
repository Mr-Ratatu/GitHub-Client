package com.github.client.data.repository

import com.github.client.data.model.UserProfile
import com.github.client.data.model.UserReposItem
import com.github.client.data.network.ApiService
import io.reactivex.Single

class ProfileRepository(private val apiService: ApiService) {

    fun getUser(userName: String): Single<UserProfile> = apiService.getUser(userName)

    fun getUserRepository(userName: String): Single<List<UserReposItem>> =
        apiService.getUserRepos(userName)

}