package edu.ucne.puntuacion.data.remote.dto

data class ReseñaDto(
    val reseñaId: Int,
    val clienteId: Int,
    val productoId: Int,
    val puntuacion: Int,
    val comentario: String,
)