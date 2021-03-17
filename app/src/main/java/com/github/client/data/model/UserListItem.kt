package com.github.client.data.model


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserListItem(
    val avatar_url: String?,
    @Json(name = "html_url")
    val htmlUrl: String,
    val id: Int,
    val login: String,
) : Parcelable