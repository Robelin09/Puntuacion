package edu.ucne.puntuacion.presentation.reseña


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.puntuacion.data.remote.dto.ClienteDto
import edu.ucne.puntuacion.data.remote.dto.ProductoDto
import edu.ucne.puntuacion.data.remote.dto.ReseñaDto
import edu.ucne.puntuacion.data.repository.ClienteRepository
import edu.ucne.puntuacion.data.repository.ProductoRepository
import edu.ucne.puntuacion.data.repository.ReseñaRepository
import edu.ucne.puntuacion.data.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReseñaViewModel @Inject constructor(
    private val reseñaRepository: ReseñaRepository,
    private val productoRepository: ProductoRepository,
    private val clienteRepository: ClienteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAllReseña()
        getAllCliente()
        getAllProducto()
    }

    fun save() {
        viewModelScope.launch {
            if (_uiState.value.puntuacion == null || _uiState.value.comentario.isNullOrBlank()) {
                _uiState.update {
                    it.copy(errorMessage = "La puntuación y el comentario no pueden estar vacíos")
                }
            } else {
                reseñaRepository.save(_uiState.value.toDto())
                nuevo()
            }
        }
    }

    fun nuevo() {
        _uiState.update {
            it.copy(
                reseñaId = null,
                clienteId = 0,
                productoId = 0,
                puntuacion = 0,
                comentario = "",
                errorMessage = null
            )
        }
    }

    fun getResena(reseñaId: Int) {
        viewModelScope.launch {
            reseñaRepository.getReseña(reseñaId).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                reseñaId = result.data?.reseñaId ?: 0,
                                clienteId = result.data?.clienteId ?: 0,
                                productoId = result.data?.productoId ?: 0,
                                puntuacion = result.data?.puntuacion ?: 0,
                                comentario = result.data?.comentario ?: "",
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun onPuntuacionChange(puntuacion: Int) {
        _uiState.update {
            it.copy(puntuacion = puntuacion)
        }
    }

    fun onComentarioChange(comentario: String) {
        _uiState.update {
            it.copy(comentario = comentario)
        }
    }
    fun onClienteChange(clienteId: Int) {
        _uiState.update {
            it.copy(clienteId = clienteId)
        }
    }
    fun onProductoChange(productoId: Int) {
        _uiState.update {
            it.copy(productoId = productoId)
        }
    }

    fun delete() {
        viewModelScope.launch {
            val response = reseñaRepository.delete(_uiState.value.reseñaId!!)
            if (response.isSuccessful) {
                nuevo()
            }
        }
    }

    private fun getAllReseña() {
        viewModelScope.launch {
            reseñaRepository.getAllReseña().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                reseñas = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }


    private fun getAllCliente() {
        viewModelScope.launch {
            clienteRepository.getAllCliente().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                clientes = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getAllProducto() {
        viewModelScope.launch {
            productoRepository.getAllProducto().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                productos = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = it.errorMessage,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
}

data class UiState(
    val reseñaId: Int? = null,
    val clienteId: Int = 0,
    val productoId: Int = 0,
    val puntuacion: Int? = null,
    val comentario: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val reseñas: List<ReseñaDto> = emptyList(),
    val productos: List<ProductoDto> = emptyList(),
    val clientes: List<ClienteDto> = emptyList()
)

fun UiState.toDto() = ReseñaDto(
    reseñaId = reseñaId ?: 0,
    clienteId = clienteId,
    productoId = productoId,
    puntuacion = puntuacion ?: 0,
    comentario = comentario
)
