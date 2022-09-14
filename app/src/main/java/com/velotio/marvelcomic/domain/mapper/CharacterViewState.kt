package com.velotio.marvelcomic.domain.mapper

import com.velotio.marvelcomic.domain.CharacterEntity
import com.velotio.marvelcomic.presentation.state.CharacterViewState

fun List<CharacterEntity>.toViewState() = map { characterEntity -> characterEntity.toViewState() }

fun CharacterEntity.toViewState() = CharacterViewState(
    id = id,
    name = name,
    description = description,
    imageUrl = imageUrl,
    bookmarkStatus = bookmarkStatus
)