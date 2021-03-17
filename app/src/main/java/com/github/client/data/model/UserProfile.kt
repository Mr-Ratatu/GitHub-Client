package com.github.client.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserProfile(
    val avatar_url: String,
    val bio: String,
    val blog: String,
    val company: Any,
    @Json(name = "created_at")
    val createdAt: String,
    val email: Any,
    @Json(name = "events_url")
    val eventsUrl: String,
    val followers: Int,
    @Json(name = "followers_url")
    val followersUrl: String,
    val following: Int,
    @Json(name = "following_url")
    val followingUrl: String,
    @Json(name = "gists_url")
    val gistsUrl: String,
    @Json(name = "gravatar_id")
    val gravatarId: String,
    val hireable: Any,
    @Json(name = "html_url")
    val htmlUrl: String,
    val id: Int,
    val location: String,
    val login: String,
    val name: String,
    @Json(name = "node_id")
    val nodeId: String,
    @Json(name = "organizations_url")
    val organizationsUrl: String,
    @Json(name = "public_gists")
    val publicGists: Int,
    @Json(name = "public_repos")
    val publicRepos: Int,
    @Json(name = "received_events_url")
    val receivedEventsUrl: String,
    @Json(name = "repos_url")
    val reposUrl: String,
    @Json(name = "site_admin")
    val siteAdmin: Boolean,
    @Json(name = "starred_url")
    val starredUrl: String,
    @Json(name = "subscriptions_url")
    val subscriptionsUrl: String,
    @Json(name = "twitter_username")
    val twitterUsername: Any,
    val type: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    val url: String
)