package com.velotio.marvelcomic.domain.mapper

import com.velotio.marvelcomic.domain.ComicsEntity
import com.velotio.marvelcomic.presentation.state.ComicViewState

fun List<ComicsEntity>.toViewState() = map { characterEntity -> characterEntity.toViewState() }

fun ComicsEntity.toViewState() = ComicViewState(
    id = id,
    title = title,
    description = description,
    imageUrl = imageUrl
)