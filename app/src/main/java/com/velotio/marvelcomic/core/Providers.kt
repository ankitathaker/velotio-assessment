package com.velotio.marvelcomic.core

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.velotio.marvelcomic.data_layer.cache.ApplicationDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
* Common methods for providers.
* */
private const val APP_DATABASE_NAME = "marvel_database.db"
const val API_BASE_URL = "http://gateway.marvel.com/v1/public/"

fun provideAppDatabase(context: Context) : ApplicationDatabase =
    Room.databaseBuilder(context, ApplicationDatabase::class.java, APP_DATABASE_NAME).fallbackToDestructiveMigration().build()

inline fun <reified T> provideRetrofitService(): T {
    val gson = GsonBuilder().setLenient().create()
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    return Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(T::class.java)
}