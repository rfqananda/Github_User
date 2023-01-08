package com.rifqiananda.githubuserv2.data.model

import com.google.gson.annotations.SerializedName

data class User(
        @field: SerializedName("login")
        val login: String,

        @field: SerializedName("id")
        val id: String,

        @field: SerializedName("avatar_url")
        val avatar: String)