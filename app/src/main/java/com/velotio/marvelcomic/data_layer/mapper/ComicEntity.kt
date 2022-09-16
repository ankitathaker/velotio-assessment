package com.velotio.marvelcomic.data_layer.mapper

import com.velotio.marvelcomic.data_layer.cache.model.ComicsCache
import com.velotio.marvelcomic.data_layer.remote.model.Comics
import com.velotio.marvelcomic.domain.ComicsEntity

fun List<Comics>.toCache(characterId: Long) = map { comics ->
    comics.toCache(characterId)
}

fun Comics.toCache(characterId: Long) = ComicsCache(
    id = id ?: 0,
    title = title ?: "",
    description = description ?: "",
    imageUrl = thumbnail?.let {
        "${it.path}.${it.extension}"
    } ?: "",
    characterId = characterId
)

fun List<Comics>.toDomain() = map { comics -> comics.toDomain() }

fun Comics.toDomain() = ComicsEntity(
    id = id ?: 0,
    title = title ?: "",
    description = description ?: "",
    imageUrl = thumbnail?.let {
        "${it.path}.${it.extension}"
    } ?: "",
)

@JvmName("characterCacheToDomain")
fun List<ComicsCache>.toDomain() = map { comic -> comic.toDomain() }

fun ComicsCache.toDomain() = ComicsEntity(
    id = id,
    title = title,
    description = description,
    imageUrl = imageUrl
)
