package com.example.pruebapokedex.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface RemoteKeysDao {
    @Query("SELECT * FROM remote_keys WHERE pokemonId = :id")
    suspend fun remoteKeysId(id: Int): RemoteKeysEntity?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(list: List<RemoteKeysEntity>)


    @Query("DELETE FROM remote_keys")
    suspend fun clear()
}