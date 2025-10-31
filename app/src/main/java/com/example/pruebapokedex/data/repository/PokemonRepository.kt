package com.example.pruebapokedex.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.example.pruebapokedex.domain.model.Pokemon


interface PokemonRepository {
    fun pagedPokemon(): Flow<PagingData<Pokemon>>
    fun favorites(): Flow<PagingData<Pokemon>>
    fun observeDetail(id: Int): Flow<Pokemon?>
    suspend fun refreshDetail(id: Int)
    suspend fun toggleFavorite(id: Int)
    suspend fun randomPokemon(): Pokemon
}