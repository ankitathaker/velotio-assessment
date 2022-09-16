package com.velotio.marvelcomic.domain.interactors

import com.velotio.marvelcomic.data_layer.DataSource
import com.velotio.marvelcomic.domain.ComicsEntity
import com.velotio.marvelcomic.domain.MarvelDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetComics(private val marvelDataRepository: MarvelDataRepository) {
    operator fun invoke(
        forceRefresh: Boolean = false,
        characterId: Long
    ): Flow<List<ComicsEntity>> {
        return flow {
            emitAll(
                marvelDataRepository.getComics(
                    dataSource = if (forceRefresh) {
                        DataSource.Network
                    } else {
                        DataSource.Cache
                    },
                    characterId = characterId
                )
            )
        }
    }
}