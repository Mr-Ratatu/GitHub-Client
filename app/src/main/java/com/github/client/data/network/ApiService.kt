package com.github.client.data.network

import com.github.client.data.model.UserListItem
import com.github.client.data.model.UserProfile
import com.github.client.data.model.UserReposItem
import com.github.client.data.model.UserResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getListUsers(): Single<List<UserListItem>>

    @GET("search/users")
    fun getSearchUsers(@Query("q") username: String): Single<UserResponse>

    @GET("users/{userName}")
    fun getUser(@Path("userName") userName: String): Single<UserProfile>

    @GET("users/{userName}/repos")
    fun getUserRepos(@Path("userName") userName: String): Single<List<UserReposItem>>

}