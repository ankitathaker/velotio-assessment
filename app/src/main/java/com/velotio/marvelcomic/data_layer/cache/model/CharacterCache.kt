package com.velotio.marvelcomic.data_layer.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.velotio.marvelcomic.data_layer.cache.dao.BaseCache

@Entity
data class CharacterCache(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String,
    val imageUrl: String,
    val bookmarkStatus: Boolean = false
) : BaseCache
