package com.velotio.marvelcomic.domain

data class CharacterEntity(
    val id: Long,
    val name: String,
    val description: String,
    val imageUrl: String,
    val bookmarkStatus: Boolean
)