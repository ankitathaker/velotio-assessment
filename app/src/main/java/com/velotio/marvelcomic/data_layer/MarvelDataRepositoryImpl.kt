package com.velotio.marvelcomic.data_layer

import com.velotio.marvelcomic.data_layer.cache.dao.CharactersDao
import com.velotio.marvelcomic.data_layer.cache.dao.ComicsDao
import com.velotio.marvelcomic.data_layer.cache.model.CharacterCache
import com.velotio.marvelcomic.data_layer.cache.model.ComicsCache
import com.velotio.marvelcomic.data_layer.mapper.toCache
import com.velotio.marvelcomic.data_layer.mapper.toDomain
import com.velotio.marvelcomic.data_layer.remote.MarvelRemoteService
import com.velotio.marvelcomic.domain.CharacterEntity
import com.velotio.marvelcomic.domain.ComicsEntity
import com.velotio.marvelcomic.domain.MarvelDataRepository
import kotlinx.coroutines.flow.*

class MarvelDataRepositoryImpl(
    private val marvelRemoteService: MarvelRemoteService,
    private val charactersDao: CharactersDao,
    private val comicsDao: ComicsDao,
) : MarvelDataRepository {

    override suspend fun getCharacters(dataSource: DataSource): Flow<List<CharacterEntity>> =
        flow {
            emitAll(
                when (dataSource) {
                    is DataSource.Cache -> getCharactersCache().map { list ->
                        if (list.isEmpty()) {
                            getCharactersNetwork()
                        } else {
                            list.toDomain()
                        }
                    }

                    is DataSource.Network -> flowOf(getCharactersNetwork())
                }
            )
        }

    private suspend fun getCharactersNetwork(): List<CharacterEntity> =
        marvelRemoteService.getCharacters().body()?.data?.results?.let { remoteData ->
            if (remoteData.isNotEmpty()) {
                charactersDao.upsert(remoteData.toCache())
            }
            remoteData.toDomain()
        } ?: emptyList()

    private fun getCharactersCache(): Flow<List<CharacterCache>> =
        charactersDao.getCharacters()

    override suspend fun getCharacter(characterId: Long): Flow<CharacterEntity> =
        charactersDao.getCharacterFlow(id = characterId).map {
            it.toDomain()
        }

    override suspend fun toggleCharacterBookmarkStatus(characterId: Long): Boolean {

        val status = charactersDao.getCharacter(characterId)?.bookmarkStatus?.not() ?: false

        return charactersDao.toggleCharacterBookmarkStatus(id = characterId, status = status) > 0
    }

    override suspend fun getComics(
        dataSource: DataSource,
        characterId: Long
    ): Flow<List<ComicsEntity>> = flow {
        emitAll(
            when (dataSource) {
                is DataSource.Cache -> getComicsCache(characterId = characterId).map { list ->
                    if (list.isEmpty()) {
                        getComicsNetwork(characterId = characterId)
                    } else {
                        list.toDomain()
                    }
                }
                is DataSource.Network -> flowOf(getComicsNetwork(characterId = characterId))
            }
        )
    }

    private suspend fun getComicsNetwork(characterId: Long): List<ComicsEntity> =
        marvelRemoteService.getComics(characterId = characterId)
            .body()?.data?.results?.let { remoteData ->
                if (remoteData.isNotEmpty()) {
                    comicsDao.upsert(remoteData.toCache(characterId = characterId))
                }
                remoteData.toDomain()
            } ?: emptyList()

    private fun getComicsCache(characterId: Long): Flow<List<ComicsCache>> =
        comicsDao.getComics(characterId = characterId)
}