package edu.ucne.puntuacion.presentation.producto


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ProductoDetailsScreen(
    productoId: Int,
    viewModel: ProductoViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    LaunchedEffect(productoId) {
        viewModel.getProducto(productoId)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ProductoDetailsBodyScreen(
        uiState = uiState,
        onNombreProductoChange = viewModel::onNombreProductoChange,
        onDescripcionChange = viewModel::onDescripcionChange,
        onPrecioChange = viewModel::onPrecioChange,
        onSaveProducto = viewModel::save,
        onDeleteProducto = viewModel::delete,
        goBack = goBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoDetailsBodyScreen(
    uiState: UiState,
    onNombreProductoChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onPrecioChange: (Double) -> Unit,
    onSaveProducto: () -> Unit,
    onDeleteProducto: () -> Unit,
    goBack: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                label = { Text("Nombre del Producto") },
                value = uiState.nombreProducto,
                onValueChange = onNombreProductoChange,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                label = { Text("Descripción") },
                value = uiState.descripcion,
                onValueChange = onDescripcionChange,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Precio") },
                value = uiState.precio.toString(),
                onValueChange = { newValue ->
                    val precio = newValue.toDoubleOrNull() ?: 0.0
                    onPrecioChange(precio)
                },
            )
            Spacer(modifier = Modifier.height(16.dp))

            uiState.errorMessage?.let {
                Text(text = it, color = Color.Red)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onSaveProducto()
                    }
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Actualizar")
                    Text("Actualizar")
                }
                OutlinedButton(
                    onClick = {
                        onDeleteProducto()
                        goBack()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    Text("Eliminar")
                }
            }
        }
    }
}
