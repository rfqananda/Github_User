package com.rifqiananda.githubuserv2.data.model

data class DetailUserResponse(
    val avatar_url: String,
    val name : String,
    val login: String,
    val id: Int,
    val company: String,
    val location: String,
    val followers: Int,
    val following: Int
)
