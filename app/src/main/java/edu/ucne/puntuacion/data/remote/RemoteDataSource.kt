package edu.ucne.puntuacion.data.remote

import edu.ucne.puntuacion.data.remote.dto.ClienteDto
import edu.ucne.puntuacion.data.remote.dto.ProductoDto
import edu.ucne.puntuacion.data.remote.dto.ReseñaDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val PuntuacionApi: PuntuacionApi
) {

    suspend fun getAllCliente() = PuntuacionApi.getAllCliente()
    suspend fun getCliente(id: Int) = PuntuacionApi.getCliente(id)
    suspend fun saveCliente(clienteDto: ClienteDto) = PuntuacionApi.saveCliente(clienteDto)
    suspend fun deleteCliente(id: Int) = PuntuacionApi.deleteCliente(id)


    suspend fun getAllProducto() = PuntuacionApi.getAllProducto()
    suspend fun getProducto(id: Int) = PuntuacionApi.getProducto(id)
    suspend fun saveProducto(productoDto: ProductoDto) = PuntuacionApi.saveProducto(productoDto)
    suspend fun deleteProducto(id: Int) = PuntuacionApi.deleteProducto(id)

    suspend fun getAllReseña() = PuntuacionApi.getAllReseña()
    suspend fun getReseña(id: Int) = PuntuacionApi.getReseña(id)
    suspend fun saveReseña(reseñaDto: ReseñaDto) = PuntuacionApi.saveReseña(reseñaDto)
    suspend fun deleteReseña(id: Int) = PuntuacionApi.deleteReseña(id)


}