package com.velotio.marvelcomic.data_layer.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.velotio.marvelcomic.data_layer.cache.dao.CharactersDao
import com.velotio.marvelcomic.data_layer.cache.dao.ComicsDao
import com.velotio.marvelcomic.data_layer.cache.model.CharacterCache
import com.velotio.marvelcomic.data_layer.cache.model.ComicsCache

/*
* Local Database
* */
@Database(
    version = 3,
    entities = [CharacterCache::class, ComicsCache::class]
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
    abstract fun comicsDao(): ComicsDao
}