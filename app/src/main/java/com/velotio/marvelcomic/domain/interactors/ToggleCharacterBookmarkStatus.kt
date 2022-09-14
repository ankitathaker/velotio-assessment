package com.velotio.marvelcomic.domain.interactors

import com.velotio.marvelcomic.domain.MarvelDataRepository

class ToggleCharacterBookmarkStatus(
    private val marvelDataRepository: MarvelDataRepository
) {
    suspend operator fun invoke(characterId: Long) =
        marvelDataRepository.toggleCharacterBookmarkStatus(characterId)
}