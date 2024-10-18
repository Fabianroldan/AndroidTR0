package com.example.unscramble.model

data class Pregunta(
    val id: Int,
    val pregunta: String,
    val opcions: List<Opcion>,
    val imatge: String,
    val continente: String,
    val dificultat: Int
)

data class Opcion(
    val resposta: String,
    val correcta: Boolean
)
