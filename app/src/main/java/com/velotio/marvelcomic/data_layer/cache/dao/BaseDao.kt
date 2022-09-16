package com.velotio.marvelcomic.data_layer.cache.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseCache

abstract class BaseDao<T : BaseCache> {

    suspend fun upsert(list: List<T>) {
        list.forEach { cache ->
            upsert(cache)
        }
    }

    open suspend fun upsert(cache: T) {
        if (insert(cache) == -1L) {
            update(cache)
        } else {
            insert(cache)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(cache: T): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun update(cache: T): Int
}