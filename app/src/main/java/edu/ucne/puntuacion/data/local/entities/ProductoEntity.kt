package edu.ucne.puntuacion.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Productos")

data class ProductoEntity(
    @PrimaryKey
    val productoId: Int? = null,
    val nombreProducto: String = "",
    val descripcion: String = "",
    val precio: Double ,
)
