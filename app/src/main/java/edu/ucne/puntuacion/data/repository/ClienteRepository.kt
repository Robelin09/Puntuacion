package edu.ucne.puntuacion.data.repository

import edu.ucne.puntuacion.data.remote.RemoteDataSource
import edu.ucne.puntuacion.data.remote.dto.ClienteDto
import edu.ucne.puntuacion.data.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject
import android.util.Log
import retrofit2.Response

class ClienteRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {

    fun getAllCliente(): Flow<Resource<List<ClienteDto>>> = flow{
        try{
            emit(Resource.Loading())
            val clientes = remoteDataSource.getAllCliente()
            emit(Resource.Success(clientes))
        } catch (e: HttpException){
            emit(Resource.Error("Error de Internet ${e.message()}"))
        }
        catch (e: Exception){
            Log.e("ClienteRepository", "getAllCliente: ${e.message}")
            emit(Resource.Error(e.message?:"Error Desconocido"))
        }
    }

    fun getCliente(id: Int): Flow<Resource<ClienteDto>> = flow{
        try{
            emit(Resource.Loading())
            val cliente = remoteDataSource.getCliente(id)
            emit(Resource.Success(cliente))
        } catch (e: HttpException){
            emit(Resource.Error("Error de Internet ${e.message()}"))
        }
        catch (e: Exception){
            Log.e("ClienteRepository", "getCliente: ${e.message}")
            emit(Resource.Error(e.message?:"Error Desconocido"))
        }
    }

    fun save(cliente: ClienteDto): Flow<Resource<ClienteDto>> = flow{
        try{
            emit(Resource.Loading())
            val cliente = remoteDataSource.saveCliente(cliente)
            emit(Resource.Success(cliente))
        }
        catch (e: HttpException){
            emit(Resource.Error("Error de Internet ${e.message()}"))
        }
        catch (e: Exception){
            Log.e("ClienteRepository", "save: ${e.message}")
            emit(Resource.Error(e.message?:"Error Desconocido"))
        }
    }
 suspend fun delete(id: Int): Response<Void?> = remoteDataSource.deleteCliente(id)

}