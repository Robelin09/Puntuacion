package edu.ucne.puntuacion.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.puntuacion.data.local.dao.ClienteDao
import edu.ucne.puntuacion.data.local.dao.ProductoDao
import edu.ucne.puntuacion.data.local.dao.ReseñaDao
import edu.ucne.puntuacion.data.local.entities.ClienteEntity
import edu.ucne.puntuacion.data.local.entities.ProductoEntity
import edu.ucne.puntuacion.data.local.entities.ReseñaEntity

@Database(
    entities = [
        ProductoEntity::class,
        ReseñaEntity::class,
        ClienteEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class PuntuacionDb : RoomDatabase() {
    abstract fun reseñaDao(): ReseñaDao
    abstract fun productoDao(): ProductoDao
    abstract fun clienteDao(): ClienteDao

}