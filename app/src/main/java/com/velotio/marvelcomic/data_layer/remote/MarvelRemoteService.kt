package com.velotio.marvelcomic.data_layer.remote

import com.velotio.marvelcomic.data_layer.remote.model.Characters
import com.velotio.marvelcomic.data_layer.remote.model.Comics
import com.velotio.marvelcomic.data_layer.remote.model.RemoteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelRemoteService {

    @GET("characters?ts=1&apikey=2f1cd72cd3d8d6fd8dcad1e0d807878e&hash=41cdf87e8b017882c8b1f829220c1186")
    suspend fun getCharacters(): Response<RemoteResponse<List<Characters>>>

    @GET("characters/{characterId}/comics?ts=1&apikey=2f1cd72cd3d8d6fd8dcad1e0d807878e&hash=41cdf87e8b017882c8b1f829220c1186")
    suspend fun getComics(@Path("characterId") characterId: Long): Response<RemoteResponse<List<Comics>>>
}