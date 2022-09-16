package com.velotio.marvelcomic.domain

import com.velotio.marvelcomic.data_layer.DataSource
import kotlinx.coroutines.flow.Flow

interface MarvelDataRepository {
    suspend fun getCharacters(dataSource: DataSource): Flow<List<CharacterEntity>>

    suspend fun getCharacter(characterId: Long): Flow<CharacterEntity>

    suspend fun toggleCharacterBookmarkStatus(characterId: Long): Boolean

    suspend fun getComics(dataSource: DataSource, characterId: Long): Flow<List<ComicsEntity>>
}