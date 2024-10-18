package com.example.unscramble.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unscramble.model.Pregunta
import com.example.unscramble.network.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(private val apiRepository: ApiRepository) : ViewModel() {
    private val _preguntas = MutableStateFlow<List<Pregunta>>(emptyList())
    val preguntas = _preguntas.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun fetchPreguntas(dificultad: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _preguntas.value = apiRepository.getPreguntasPorDificultad(dificultad)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
