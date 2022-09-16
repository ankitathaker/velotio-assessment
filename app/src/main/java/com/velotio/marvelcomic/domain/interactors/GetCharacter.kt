package com.velotio.marvelcomic.domain.interactors

import com.velotio.marvelcomic.domain.CharacterEntity
import com.velotio.marvelcomic.domain.MarvelDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetCharacter(
    private val marvelDataRepository: MarvelDataRepository
) {
    operator fun invoke(characterId: Long): Flow<CharacterEntity> =
        flow {
            emitAll(
                marvelDataRepository.getCharacter(characterId = characterId)
            )
        }
}