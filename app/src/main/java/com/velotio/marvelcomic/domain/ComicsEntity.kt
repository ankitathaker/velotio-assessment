package com.velotio.marvelcomic.domain

data class ComicsEntity(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String,
)