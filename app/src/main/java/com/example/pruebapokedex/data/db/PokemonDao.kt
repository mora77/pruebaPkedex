package com.example.pruebapokedex.data.db

import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon ORDER BY id ASC")
    fun paging(): PagingSource<Int, PokemonEntity>


    @Query("SELECT * FROM pokemon WHERE isFavorite = 1 ORDER BY name ASC")
    fun favoritesPaging(): PagingSource<Int, PokemonEntity>


    @Query("SELECT * FROM pokemon WHERE id = :id")
    fun observeById(id: Int): Flow<PokemonEntity?>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(list: List<PokemonEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: PokemonEntity)


    @Query("UPDATE pokemon SET isFavorite = :fav WHERE id = :id")
    suspend fun setFavorite(id: Int, fav: Boolean)
}