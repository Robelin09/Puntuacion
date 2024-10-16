package edu.ucne.puntuacion.data.repository

import android.util.Log
import edu.ucne.puntuacion.data.remote.RemoteDataSource
import edu.ucne.puntuacion.data.remote.dto.ClienteDto
import edu.ucne.puntuacion.data.remote.dto.ReseñaDto
import edu.ucne.puntuacion.data.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class ReseñaRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getAllReseña(): Flow<Resource<List<ReseñaDto>>> = flow{
        try{
            emit(Resource.Loading())
            val reseñas = remoteDataSource.getAllReseña()
            emit(Resource.Success(reseñas))
        } catch (e: HttpException){
            emit(Resource.Error("Error de Internet ${e.message()}"))
        }
        catch (e: Exception){
            Log.e("ReseñaRepository", "getAllReseña: ${e.message}")
            emit(Resource.Error(e.message?:"Error Desconocido"))
        }
    }

    fun getReseña(id: Int): Flow<Resource<ReseñaDto>> = flow{
        try{
            emit(Resource.Loading())
            val reseña = remoteDataSource.getReseña(id)
            emit(Resource.Success(reseña))
        } catch (e: HttpException){
            emit(Resource.Error("Error de Internet ${e.message()}"))
        }
        catch (e: Exception){
            Log.e("ReseñaRepository", "getReseña: ${e.message}")
            emit(Resource.Error(e.message?:"Error Desconocido"))
        }
    }



    suspend fun delete(id: Int): Response<Void?> = remoteDataSource.deleteReseña(id)
    suspend fun save(reseña: ReseñaDto) = remoteDataSource.saveReseña(reseña)
}