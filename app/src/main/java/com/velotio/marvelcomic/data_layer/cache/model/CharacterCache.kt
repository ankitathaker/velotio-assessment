package com.velotio.marvelcomic.data_layer.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterCache(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String,
    val imageUrl: String,
    val bookmarkStatus: Boolean = false
)
