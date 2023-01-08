package com.rifqiananda.githubuserv2.api

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    val endpoint: ApiEndpoint
    get(){
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient().newBuilder()
        val client = OkHttpClient.Builder().apply {
            builder.addInterceptor { chain ->
                val request: Request = chain.request().newBuilder().addHeader("Authorization", "token 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").build()
                chain.proceed(request)
            }
        }
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiEndpoint::class.java)
    }
}