package com.velotio.marvelcomic.presentation.state

data class ComicViewState(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String
)
