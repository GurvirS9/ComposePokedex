package com.compose.pookiedex.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.pookiedex.data.PokemonResponse
import com.compose.pookiedex.data.apiService
import kotlinx.coroutines.launch

class PokeViewModel : ViewModel() {
    private val _pokemon = mutableStateOf<PokemonResponse?>(null)
    val pokemon = _pokemon

    private val _isLoading = mutableStateOf(false)
    val isLoading = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error = _error

    fun searchPokemon(query: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                _pokemon.value = apiService.getPokemon(query.lowercase())
            } catch (e: Exception) {
                Log.e("PokeViewModel", "Error fetching pokemon", e)
                _error.value = "Error: ${e.localizedMessage}"
                _pokemon.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
}