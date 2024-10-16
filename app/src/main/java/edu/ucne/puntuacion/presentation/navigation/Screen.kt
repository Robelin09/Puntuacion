package edu.ucne.puntuacion.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    data object ClienteList : Screen()
    @Serializable
    data object ProductoList : Screen()
    @Serializable
    data object ReseñaList : Screen()

    @Serializable
    data class Cliente (val clienteId: Int) : Screen()
    @Serializable
    data class Producto (val productoId: Int) : Screen()
    @Serializable
    data class Reseña (val reseñaId: Int) : Screen()

    @Serializable
    data class ProductoDetails(val productoId: Int) : Screen()
    @Serializable
    data class ClienteDetails(val clienteId: Int) : Screen()
    @Serializable
    data class ReseñaDetails(val reseñaId: Int) : Screen()
}