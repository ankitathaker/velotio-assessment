package com.velotio.marvelcomic.data_layer.mapper

import com.velotio.marvelcomic.data_layer.cache.model.CharacterCache
import com.velotio.marvelcomic.data_layer.remote.Characters
import com.velotio.marvelcomic.domain.CharacterEntity


fun List<Characters>.toCache() = map { character -> character.toCache() }

fun Characters.toCache() = CharacterCache(
    id = id,
    name = name,
    description = description,
    imageUrl = "${thumbnail.path}.${thumbnail.extension}"
)

fun List<Characters>.toDomain() = map { character -> character.toDomain() }

fun Characters.toDomain() = CharacterEntity(
    id = id,
    name = name,
    description = description,
    imageUrl = "${thumbnail.path}.${thumbnail.extension}",
    bookmarkStatus = false
)

@JvmName("characterCacheToDomain")
fun List<CharacterCache>.toDomain() = map { character -> character.toDomain() }

fun CharacterCache.toDomain() = CharacterEntity(
    id = id,
    name = name,
    description = description,
    imageUrl = imageUrl,
    bookmarkStatus = bookmarkStatus
)

