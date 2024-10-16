package edu.ucne.puntuacion.presentation.producto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.puntuacion.data.remote.dto.ProductoDto
import edu.ucne.puntuacion.data.repository.ProductoRepository
import edu.ucne.puntuacion.data.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductoViewModel @Inject constructor(
    private val productoRepository: ProductoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAllProducto()
    }

    fun save() {
        viewModelScope.launch {
            if (_uiState.value.nombreProducto.isNullOrBlank() ||  _uiState.value.precio <= 0 ) {
                _uiState.update {
                    it.copy(errorMessage = "El nombre del producto y el precio no pueden estar vacíos o ser inválidos")
                }
            } else {
                productoRepository.save(_uiState.value.toDto())
                nuevo()
            }
        }
    }

    fun nuevo() {
        _uiState.update {
            it.copy(
                productoId = null,
                nombreProducto = "",
                descripcion = "",
                precio = 0.0,
                errorMessage = null
            )
        }
    }

    fun getProducto(productoId: Int) {
        viewModelScope.launch {
            productoRepository.getProducto(productoId).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                productoId = result.data?.productoId ?: 0,
                                nombreProducto = result.data?.nombreProducto ?: "",
                                descripcion = result.data?.descripcion ?: "",
                                precio = result.data?.precio ?: 0.0,
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

    fun onNombreProductoChange(nombreProducto: String) {
        _uiState.update {
            it.copy(nombreProducto = nombreProducto)
        }
    }

    fun onDescripcionChange(descripcion: String) {
        _uiState.update {
            it.copy(descripcion = descripcion)
        }
    }

    fun onPrecioChange(precio: Double) {
        _uiState.update {
            it.copy(precio = precio)
        }
    }

    fun delete() {
        viewModelScope.launch {
            val response = productoRepository.delete(_uiState.value.productoId!!)
            if (response.isSuccessful) {
                nuevo()
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
    val productoId: Int? = null,
    val nombreProducto: String = "",
    val descripcion: String = "",
    val precio: Double = 0.0,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val productos: List<ProductoDto> = emptyList()
)

fun UiState.toDto() = ProductoDto(
    productoId = productoId ?: 0,
    nombreProducto = nombreProducto,
    descripcion = descripcion,
    precio = precio
)
