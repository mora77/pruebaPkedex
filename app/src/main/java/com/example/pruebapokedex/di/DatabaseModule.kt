package com.example.pruebapokedex.di

import android.content.Context
import androidx.room.Room
import com.example.pruebapokedex.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDb(@ApplicationContext ctx: Context): AppDatabase = Room.databaseBuilder(
        ctx, AppDatabase::class.java, "pokedex.db"
    ).fallbackToDestructiveMigration().build()
}