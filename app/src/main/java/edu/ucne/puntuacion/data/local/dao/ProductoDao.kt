package edu.ucne.puntuacion.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert
import edu.ucne.puntuacion.data.local.entities.ProductoEntity
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Upsert
    suspend fun save(producto: ProductoEntity)

    @Query("""
        SELECT * 
        FROM Productos
        WHERE productoId=:id
        LIMIT 1
    """)
    suspend fun find(id: Int): ProductoEntity?

    @Query("""
        SELECT *
        FROM Productos
        WHERE LOWER(nombreProducto) = LOWER(:nombre)
        LIMIT 1
    """)
    suspend fun findByNombre(nombre: String): ProductoEntity?

    @Query("""
        SELECT *
        FROM Productos
        WHERE LOWER(descripcion) LIKE LOWER('%' || :descripcion || '%')
        LIMIT 1
    """)
    suspend fun findByDescripcion(descripcion: String): ProductoEntity?

    @Query("""
        SELECT *
        FROM Productos
        WHERE precio=:precio
        LIMIT 1
    """)
    suspend fun findByPrecio(precio: Double): ProductoEntity?


    @Delete
    suspend fun delete (ticket: ProductoEntity)
    @Query("SELECT * FROM Productos")
    fun getAll(): Flow<List<ProductoEntity>>
}