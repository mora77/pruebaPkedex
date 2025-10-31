package com.example.pruebapokedex.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey val pokemonId: Int,
    val nextOffset: Int?
)