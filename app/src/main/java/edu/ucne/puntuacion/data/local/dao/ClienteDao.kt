package edu.ucne.puntuacion.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.puntuacion.data.local.entities.ClienteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {
    @Upsert
    suspend fun save (cliente: ClienteEntity)
    @Query("""
        SELECT * 
        FROM Clientes
        WHERE clienteId=:Id
        LIMIT 1
    """)
    suspend fun find (Id: Int): ClienteEntity?

    @Query(
        """
            SELECT *
            FROM Clientes
            WHERE LOWER(:nombre)
            LIMIT 1
        """
    )
    suspend fun findByNombre(nombre: String): ClienteEntity?


    @Query(
        """
            SELECT *
            FROM Clientes
            WHERE LOWER(:direccion)
            LIMIT 1
        """
    )
    suspend fun findByDirecccion(direccion: String): ClienteEntity?

    @Query(
        """
            SELECT *
            FROM Clientes
            WHERE LOWER(:telefono)
            LIMIT 1
        """
    )
    suspend fun findByTelefono(telefono: String): ClienteEntity?

    @Delete
    suspend fun delete (ticket: ClienteEntity)
    @Query("SELECT * FROM Clientes")
    fun getAll(): Flow<List<ClienteEntity>>

}