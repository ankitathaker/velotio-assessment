package com.velotio.marvelcomic.domain.interactors

import com.velotio.marvelcomic.data_layer.DataSource
import com.velotio.marvelcomic.domain.CharacterEntity
import com.velotio.marvelcomic.domain.MarvelDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetCharacters(
    private val marvelDataRepository: MarvelDataRepository
) {
    operator fun invoke(forceRefresh: Boolean = false): Flow<List<CharacterEntity>> {
        return flow {
            emitAll(
                marvelDataRepository.getCharacters(
                    if (forceRefresh) {
                        DataSource.Network
                    } else {
                        DataSource.Cache
                    }
                )
            )
        }
    }
}