package com.velotio.marvelcomic.data_layer.cache.dao

import androidx.room.Dao
import androidx.room.Query
import com.velotio.marvelcomic.data_layer.cache.model.ComicsCache
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ComicsDao : BaseDao<ComicsCache>() {

    @Query("SELECT * FROM ComicsCache WHERE characterId = :characterId")
    abstract fun getComics(characterId: Long): Flow<List<ComicsCache>>
}