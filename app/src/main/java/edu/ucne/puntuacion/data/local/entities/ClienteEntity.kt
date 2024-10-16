package edu.ucne.puntuacion.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Clientes")

data class ClienteEntity(
    @PrimaryKey
    val clienteId: Int? = null,
    val nombre: String = "",
    val direccion: String = "",
    val telefono: String = "",
)