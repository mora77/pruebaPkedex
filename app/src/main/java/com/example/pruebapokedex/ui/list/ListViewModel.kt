package com.example.pruebapokedex.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pruebapokedex.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(
    private val repo: PokemonRepository
) : ViewModel() {
    val paging = repo.pagedPokemon().cachedIn(viewModelScope)
    suspend fun toggleFavorite(id: Int) = repo.toggleFavorite(id)
}