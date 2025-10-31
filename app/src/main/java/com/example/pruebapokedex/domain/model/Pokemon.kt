package com.example.pruebapokedex.domain.model

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val spriteUrl: String?,
    val height: Int,
    val weight: Int,
    val types: List<String>,
    val isFavorite: Boolean
)