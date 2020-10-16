package com.github.client.network

import com.github.client.model.UserListItem
import com.github.client.model.UserProfile
import com.github.client.model.UserReposItem
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users")
    fun getListUsers(): Single<List<UserListItem>>

    @GET("users/{userName}")
    fun getUser(@Path("userName") userName: String): Single<UserProfile>

    @GET("users/{userName}/repos")
    fun getUserRepos(@Path("userName") userName: String): Single<List<UserReposItem>>

}