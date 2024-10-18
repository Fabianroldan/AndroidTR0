package com.example.unscramble.network

import com.example.unscramble.model.Pregunta

class ApiRepository(private val apiService: ApiService) {
    suspend fun getPreguntasPorDificultad(dificultad: Int): List<Pregunta> {
        return apiService.getPreguntas(dificultad)
    }
}
