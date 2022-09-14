package com.velotio.marvelcomic.presentation.state

data class CharacterViewState(
    val id: Long,
    val name: String,
    val description: String,
    val imageUrl: String,
    val bookmarkStatus: Boolean
)
