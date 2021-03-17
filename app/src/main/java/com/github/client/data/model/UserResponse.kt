package com.github.client.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("items")
    val userList: List<UserListItem>
)