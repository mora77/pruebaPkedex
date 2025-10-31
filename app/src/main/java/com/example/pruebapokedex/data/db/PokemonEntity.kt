package com.example.pruebapokedex.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String?, // official artwork
    val spriteUrl: String?, // front_default
    val height: Int,
    val weight: Int,
    val typesCsv: String, // "grass,poison"
    val isFavorite: Boolean = false
)