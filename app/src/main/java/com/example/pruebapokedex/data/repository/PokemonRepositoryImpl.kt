package com.example.pruebapokedex.data.repository

import androidx.paging.*
import com.example.pruebapokedex.data.api.PokemonApi
import com.example.pruebapokedex.data.db.*
import com.example.pruebapokedex.data.paging.PokemonRemoteMediator
import com.example.pruebapokedex.domain.model.Pokemon
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApi,
    private val db: AppDatabase
) : PokemonRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun pagedPokemon(): Flow<PagingData<Pokemon>> =
        Pager(
            config = PagingConfig(pageSize = 25),
            remoteMediator = PokemonRemoteMediator(api, db),
            pagingSourceFactory = { db.pokemonDao().paging() }
        ).flow.map { paging -> paging.map { it.toDomain() } }


    override fun favorites(): Flow<PagingData<Pokemon>> =
        Pager(PagingConfig(pageSize = 25)) { db.pokemonDao().favoritesPaging() }
            .flow.map { it.map { e -> e.toDomain() } }


    override fun observeDetail(id: Int): Flow<Pokemon?> =
        db.pokemonDao().observeById(id).map { it?.toDomain() }


    override suspend fun refreshDetail(id: Int) {
        val d = api.getPokemon(id.toString())
        db.pokemonDao().upsert(
            PokemonEntity(
                id = d.id,
                name = d.name.replaceFirstChar { it.uppercase() },
                imageUrl = d.sprites.other?.officialArtwork?.frontDefault,
                spriteUrl = d.sprites.frontDefault,
                height = d.height,
                weight = d.weight,
                typesCsv = d.types.sortedBy { it.slot }.joinToString(",") { it.type.name }
            )
        )
    }


    override suspend fun toggleFavorite(id: Int) {
        val current = db.pokemonDao().observeById(id).first() ?: return
        db.pokemonDao().setFavorite(id, !current.isFavorite)
    }


    override suspend fun randomPokemon(): Pokemon {
        val count = api.listPokemon(limit = 1, offset = 0).count
        val randomId = (1..count).random()
        val d = api.getPokemon(randomId.toString())
        val entity = PokemonEntity(
            id = d.id,
            name = d.name.replaceFirstChar { it.uppercase() },
            imageUrl = d.sprites.other?.officialArtwork?.frontDefault,
            spriteUrl = d.sprites.frontDefault,
            height = d.height,
            weight = d.weight,
            typesCsv = d.types.sortedBy { it.slot }.joinToString(",") { it.type.name }
        )
        db.pokemonDao().upsert(entity)
        return entity.toDomain()
    }
}


private fun PokemonEntity.toDomain() = Pokemon(
    id = id,
    name = name,
    imageUrl = imageUrl,
    spriteUrl = spriteUrl,
    height = height,
    weight = weight,
    types = if (typesCsv.isBlank()) emptyList() else typesCsv.split(","),
    isFavorite = isFavorite
)