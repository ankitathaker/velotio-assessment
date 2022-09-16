package com.velotio.marvelcomic.data_layer.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.velotio.marvelcomic.data_layer.cache.dao.BaseCache

@Entity
data class ComicsCache(
    @PrimaryKey
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String,
    val characterId: Long
) : BaseCache
