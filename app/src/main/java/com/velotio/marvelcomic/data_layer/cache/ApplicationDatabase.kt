package com.velotio.marvelcomic.data_layer.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.velotio.marvelcomic.data_layer.cache.dao.CharactersDao
import com.velotio.marvelcomic.data_layer.cache.model.CharacterCache

/*
* Local Database
* */
@Database(version = 1, entities = [CharacterCache::class])
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
}