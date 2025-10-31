package com.example.pruebapokedex.data.dto

import com.squareup.moshi.Json


data class PokemonListDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<NamedApiResource>
)


data class NamedApiResource(val name: String, val url: String)


data class PokemonDetailDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val types: List<PokemonTypeSlot>
)


data class Sprites(
    @Json(name = "front_default") val frontDefault: String?,
    val other: OtherSprites?
)


data class OtherSprites(
    @Json(name = "official-artwork") val officialArtwork: OfficialArtwork?
)


data class OfficialArtwork(
    @Json(name = "front_default") val frontDefault: String?
)


data class PokemonTypeSlot(
    val slot: Int,
    val type: NamedApiResource
)