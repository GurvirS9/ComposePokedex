package com.compose.pookiedex.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

val client = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .build()

val apiService = Retrofit.Builder()
    .baseUrl("https://pokeapi.co/api/v2/")
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(PokeApi::class.java)