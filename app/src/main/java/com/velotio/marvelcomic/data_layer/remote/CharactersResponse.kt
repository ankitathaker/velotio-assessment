package com.velotio.marvelcomic.data_layer.remote

data class RemoteResponse(
    val data: CharactersResponse
)

data class CharactersResponse(
    val results: List<Characters>
)

data class Characters(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)

data class Thumbnail(
    val path: String,
    val extension: String
)