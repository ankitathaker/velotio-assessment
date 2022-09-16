package com.velotio.marvelcomic.data_layer.cache.dao

import androidx.room.Dao
import androidx.room.Query
import com.velotio.marvelcomic.data_layer.cache.model.CharacterCache
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CharactersDao : BaseDao<CharacterCache>() {

    override suspend fun upsert(cache: CharacterCache) {
        super.upsert(
            cache = getCharacter(cache.id)?.let { availableCache ->
                cache.copy(
                    bookmarkStatus = availableCache.bookmarkStatus
                )
            } ?: cache
        )
    }

    @Query("SELECT * FROM CharacterCache")
    abstract fun getCharacters(): Flow<List<CharacterCache>>

    @Query("SELECT * FROM CharacterCache WHERE id=:id")
    abstract suspend fun getCharacter(id: Long): CharacterCache?

    @Query("SELECT * FROM CharacterCache WHERE id=:id")
    abstract fun getCharacterFlow(id: Long): Flow<CharacterCache>

    @Query("UPDATE CharacterCache SET bookmarkStatus = :status WHERE id = :id")
    abstract suspend fun toggleCharacterBookmarkStatus(id: Long, status: Boolean): Int
}