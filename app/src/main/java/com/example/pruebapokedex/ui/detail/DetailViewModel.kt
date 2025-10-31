package com.example.pruebapokedex.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebapokedex.data.repository.PokemonRepository
import com.example.pruebapokedex.domain.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repo: PokemonRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val id: Int = savedStateHandle["id"] ?: error("id requerido")
    val pokemon: StateFlow<Pokemon?> =
        repo.observeDetail(id).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    init {
        viewModelScope.launch { repo.refreshDetail(id) }
    }

    fun toggleFavorite() = viewModelScope.launch { repo.toggleFavorite(id) }
}