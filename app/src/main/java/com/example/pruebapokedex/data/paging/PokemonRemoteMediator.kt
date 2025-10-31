package com.example.pruebapokedex.data.paging

import androidx.paging.*
import androidx.room.withTransaction
import com.example.pruebapokedex.data.api.PokemonApi
import com.example.pruebapokedex.data.db.*


@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val api: PokemonApi,
    private val db: AppDatabase
) : RemoteMediator<Int, PokemonEntity>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        val pageSize = 25
        val pokemonDao = db.pokemonDao()
        val keysDao = db.remoteKeysDao()


        val offset = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(true)
                val key = keysDao.remoteKeysId(lastItem.id)
                key?.nextOffset ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }


        return try {
            val list = api.listPokemon(limit = pageSize, offset = offset)
            val detailed = list.results.map { r ->
                val id = r.url.trimEnd('/').substringAfterLast('/').toInt()
                val d = api.getPokemon(id.toString())
                PokemonEntity(
                    id = d.id,
                    name = d.name.replaceFirstChar { it.uppercase() },
                    imageUrl = d.sprites.other?.officialArtwork?.frontDefault,
                    spriteUrl = d.sprites.frontDefault,
                    height = d.height,
                    weight = d.weight,
                    typesCsv = d.types.sortedBy { it.slot }.joinToString(",") { it.type.name }
                )
            }


            val endReached = list.next == null
            val nextOffset = if (endReached) null else offset + pageSize


            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    keysDao.clear()
                }
                pokemonDao.upsertAll(detailed)
                val keys = detailed.map { e ->
                    RemoteKeysEntity(
                        pokemonId = e.id,
                        nextOffset = nextOffset
                    )
                }
                keysDao.upsertAll(keys)
            }


            MediatorResult.Success(endOfPaginationReached = endReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}