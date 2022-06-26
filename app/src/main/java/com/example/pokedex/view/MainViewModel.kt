package com.example.pokedex.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.netwrok.data.PokemonInfoResult
import com.example.pokedex.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    val pokomonInfoFlow = MutableStateFlow(PokemonInfoResult(emptyMap(), emptyList()))

    fun getPokemonInfo(
        index: Int
    ) = viewModelScope.launch {
        repository.getPokemonInfo(
            index = index,
            onSuccessListener = {
                pokomonInfoFlow.value = it
            },
            onFailureListener = {

            }
        )
    }

}