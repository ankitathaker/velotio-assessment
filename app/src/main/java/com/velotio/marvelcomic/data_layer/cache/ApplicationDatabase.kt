package com.velotio.marvelcomic.data_layer.cache

import androidx.room.Database
import androidx.room.RoomDatabase
/*
* Local Database
* */
@Database(version = 1, entities = [])
abstract class ApplicationDatabase: RoomDatabase() {
}