package edu.ucne.puntuacion.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.puntuacion.data.local.entities.ReseñaEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ReseñaDao {
    @Upsert
    suspend fun save(reseña: ReseñaEntity)

    @Query("""
        SELECT * 
        FROM Reseñas
        WHERE reseñaId=:id
        LIMIT 1
    """)
    suspend fun find(id: Int): ReseñaEntity?


    @Query("""
        SELECT *
        FROM Reseñas
        WHERE puntuacion=:puntuacion
    """)
    suspend fun findByPuntuacion(puntuacion: Int): List<ReseñaEntity>

    @Delete
    suspend fun delete(reseña: ReseñaEntity)

    @Query("SELECT * FROM Reseñas")
    fun getAll(): Flow<List<ReseñaEntity>>
}