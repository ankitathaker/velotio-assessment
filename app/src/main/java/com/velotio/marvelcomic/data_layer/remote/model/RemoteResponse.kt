package com.velotio.marvelcomic.data_layer.remote.model

data class RemoteResponse<T>(
    val data: ResponseResults<T>
)

data class ResponseResults<T>(
    val results: T
)