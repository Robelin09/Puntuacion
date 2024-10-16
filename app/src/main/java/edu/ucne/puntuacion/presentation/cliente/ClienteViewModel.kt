package edu.ucne.puntuacion.presentation.cliente


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.puntuacion.data.remote.dto.ClienteDto
import edu.ucne.puntuacion.data.repository.ClienteRepository
import edu.ucne.puntuacion.data.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClienteViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAllCliente()
    }

    fun save() {
        viewModelScope.launch {
            if (_uiState.value.nombre.isNullOrBlank() || _uiState.value.telefono.isNullOrBlank()) {
                _uiState.update {
                    it.copy(errorMessage = "El nombre y el teléfono no pueden estar vacíos")
                }
            } else {
                clienteRepository.save(_uiState.value.toDto())
                nuevo()
            }
        }
    }

    fun nuevo() {
        _uiState.update {
            it.copy(
                clienteId = null,
                nombre = "",
                direccion = "",
                telefono = "",
                errorMessage = null
            )
        }
    }

    fun getCliente(clienteId: Int) {
        viewModelScope.launch {
            clienteRepository.getCliente(clienteId).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                clienteId = result.data?.clienteId ?: 0,
                                nombre = result.data?.nombre ?: "",
                                direccion = result.data?.direccion ?: "",
                                telefono = result.data?.telefono ?: "",
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

    fun onNombreChange(nombre: String) {
        _uiState.update {
            it.copy(nombre = nombre)
        }
    }

    fun onDireccionChange(direccion: String) {
        _uiState.update {
            it.copy(direccion = direccion)
        }
    }

    fun onTelefonoChange(telefono: String) {
        _uiState.update {
            it.copy(telefono = telefono)
        }
    }


    fun delete() {
        viewModelScope.launch {
            val response = clienteRepository.delete(_uiState.value.clienteId!!)
            if (response.isSuccessful) {
                nuevo()
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
}

data class UiState(
    val clienteId: Int? = null,
    val nombre: String = "",
    val direccion: String = "",
    val telefono: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val clientes: List<ClienteDto> = emptyList()
)

fun UiState.toDto() = ClienteDto(
    clienteId = clienteId ?: 0,
    nombre = nombre,
    direccion = direccion,
    telefono = telefono
)
