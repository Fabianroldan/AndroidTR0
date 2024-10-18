package com.example.unscramble.network

import com.example.unscramble.model.Pregunta
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("preguntas")
    suspend fun getPreguntas(@Query("dificultad") dificultad: Int): List<Pregunta>
}
