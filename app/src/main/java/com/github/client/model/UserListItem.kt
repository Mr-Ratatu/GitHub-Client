package com.github.client.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserListItem(
    val avatar_url: String?,
    @Json(name = "html_url")
    val htmlUrl: String,
    val id: Int,
    val login: String,
)