package edu.ucne.puntuacion.data.repository

import android.util.Log
import edu.ucne.puntuacion.data.remote.RemoteDataSource
import edu.ucne.puntuacion.data.remote.dto.ClienteDto
import edu.ucne.puntuacion.data.remote.dto.ProductoDto
import edu.ucne.puntuacion.data.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class ProductoRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
){
    fun getAllProducto(): Flow<Resource<List<ProductoDto>>> = flow{
        try{
            emit(Resource.Loading())
            val productos = remoteDataSource.getAllProducto()
            emit(Resource.Success(productos))
        } catch (e: HttpException){
            emit(Resource.Error("Error de Internet ${e.message()}"))
        }
        catch (e: Exception){
            Log.e("ProductoRepository", "getAllProducto: ${e.message}")
            emit(Resource.Error(e.message?:"Error Desconocido"))
        }
    }

    fun getProducto(id: Int): Flow<Resource<ProductoDto>> = flow{
        try{
            emit(Resource.Loading())
            val producto = remoteDataSource.getProducto(id)
            emit(Resource.Success(producto))
        } catch (e: HttpException){
            emit(Resource.Error("Error de Internet ${e.message()}"))
        }
        catch (e: Exception){
            Log.e("ProductoRepository", "getProducto: ${e.message}")
            emit(Resource.Error(e.message?:"Error Desconocido"))
        }
    }
    suspend fun save(producto: ProductoDto) = remoteDataSource.saveProducto(producto)
    suspend fun delete(id: Int): Response<Void?> = remoteDataSource.deleteProducto(id)
}
