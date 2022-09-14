package com.velotio.marvelcomic.data_layer.cache.dao

import androidx.room.*
import com.velotio.marvelcomic.data_layer.cache.model.CharacterCache
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {

    suspend fun upsert(list: List<CharacterCache>) {
        list.forEach { cache ->
            upsert(cache)
        }
    }

    suspend fun upsert(cache: CharacterCache) {
        getCharacter(cache.id)?.let { availableCache ->
            update(
                characterCache = cache.copy(
                    bookmarkStatus = availableCache.bookmarkStatus
                )
            )
        } ?: insert(cache)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(characterCache: CharacterCache): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(characterCache: CharacterCache): Int

    @Query("SELECT * FROM CharacterCache")
    fun getCharacters(): Flow<List<CharacterCache>>

    @Query("SELECT * FROM CharacterCache WHERE id=:id")
    suspend fun getCharacter(id: Long): CharacterCache?

    @Query("UPDATE CharacterCache SET bookmarkStatus = :status WHERE id = :id")
    suspend fun toggleCharacterBookmarkStatus(id: Long, status: Boolean): Int
}