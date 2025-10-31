package com.example.pruebapokedex.di

import com.example.pruebapokedex.data.api.PokemonApi
import com.example.pruebapokedex.data.db.AppDatabase
import com.example.pruebapokedex.data.repository.PokemonRepositoryImpl
import com.example.pruebapokedex.data.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    @Singleton
    fun providePokemonRepository(api: PokemonApi, db: AppDatabase): PokemonRepository =
        PokemonRepositoryImpl(api, db)
}