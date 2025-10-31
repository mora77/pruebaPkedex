package com.example.pruebapokedex.ui.radar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pruebapokedex.data.repository.PokemonRepository
import com.example.pruebapokedex.domain.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RadarViewModel @Inject constructor(
    private val repo: PokemonRepository
) : ViewModel() {
    var found by mutableStateOf<Pokemon?>(null)
        private set

    suspend fun getRandom(): Pokemon {
        val p = repo.randomPokemon(); found = p; return p
    }
}