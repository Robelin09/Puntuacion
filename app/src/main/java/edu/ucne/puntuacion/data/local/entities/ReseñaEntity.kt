package edu.ucne.puntuacion.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "Reseñas",
    foreignKeys = [
        ForeignKey(
            entity = ClienteEntity::class,


            parentColumns = ["clienteId"],
            childColumns = ["clienteId"]
        )
    ]
)

data class ReseñaEntity(
    @PrimaryKey
    val reseñaId: Int? = null,
    val clienteId: Int,
    val productoId: Int,
    val puntuacion: Int?,
    val comentario: String = ""
)
