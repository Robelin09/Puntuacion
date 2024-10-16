package edu.ucne.puntuacion.data.remote

import edu.ucne.puntuacion.data.remote.dto.ClienteDto
import edu.ucne.puntuacion.data.remote.dto.ProductoDto
import edu.ucne.puntuacion.data.remote.dto.ReseñaDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PuntuacionApi {

    @GET("api/Clientes/{id}")
    suspend fun getCliente(@Path("id") id: Int): ClienteDto

    @GET("api/Clientes")
    suspend fun getAllCliente(): List<ClienteDto>

    @POST("api/Clientes")
    suspend fun saveCliente(@Body clienteDto: ClienteDto?): ClienteDto

    @DELETE("api/Clientes/{id}")
    suspend fun deleteCliente(@Path("id") id: Int): Response<Void?>



    @GET ("api/Productos/{id}")
    suspend fun getProducto(@Path("id") id: Int): ProductoDto

    @GET ("api/Productos")
    suspend fun getAllProducto(): List<ProductoDto>

    @POST ("api/Productos")
    suspend fun saveProducto(@Body productoDto: ProductoDto?): ProductoDto

    @DELETE ("api/Productos/{id}")
    suspend fun deleteProducto(@Path("id") id: Int): Response<Void?>




    @GET ("api/Reseñas/{id}")
    suspend fun getReseña(@Path("id") id: Int): ReseñaDto

    @GET ("api/Reseñas")
    suspend fun getAllReseña(): List<ReseñaDto>

    @POST ("api/Reseñas")
    suspend fun saveReseña(@Body reseñaDto: ReseñaDto?): ReseñaDto

    @DELETE ("api/Reseñas/{id}")
    suspend fun deleteReseña(@Path("id") id: Int): Response<Void?>

}