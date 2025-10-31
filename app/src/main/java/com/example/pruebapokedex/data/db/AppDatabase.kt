package com.example.pruebapokedex.data.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [PokemonEntity::class, RemoteKeysEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}