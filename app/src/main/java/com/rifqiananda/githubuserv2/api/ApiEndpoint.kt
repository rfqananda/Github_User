package com.rifqiananda.githubuserv2.api

import com.rifqiananda.githubuserv2.data.model.DetailUserResponse
import com.rifqiananda.githubuserv2.data.model.User
import com.rifqiananda.githubuserv2.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndpoint {

    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ) : Call<UserResponse>
    fun getData(
        @Query("q") query: String
    ) : Call<UserResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}