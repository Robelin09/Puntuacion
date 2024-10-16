package edu.ucne.puntuacion.presentation.reseña


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ReseñaScreen(
    viewModel: ReseñaViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ReseñaBodyScreen(
        uiState = uiState,
        onClienteChange = viewModel::onClienteChange,
        onProductoChange = viewModel::onProductoChange,
        onPuntuacionChange = viewModel::onPuntuacionChange,
        onComentarioChange = viewModel::onComentarioChange,
        onSaveReseña = viewModel::save,
        onNuevoReseña = viewModel::nuevo,
        goBack = goBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReseñaBodyScreen(
    uiState: UiState,
    onClienteChange: (Int) -> Unit,
    onProductoChange: (Int) -> Unit,
    onPuntuacionChange: (Int) -> Unit,
    onComentarioChange: (String) -> Unit,
    onSaveReseña: () -> Unit,
    onNuevoReseña: () -> Unit,
    goBack: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            ClienteDropdown(uiState = uiState, onClienteChange = onClienteChange)
            ProductoDropdown(uiState = uiState, onProductoChange = onProductoChange)

            OutlinedTextField(
                label = { Text("Puntuación") },
                value = uiState.puntuacion?.toString() ?: "",
                onValueChange = { puntuacion -> onPuntuacionChange(puntuacion.toIntOrNull() ?: 0) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                label = { Text("Comentario") },
                value = uiState.comentario,
                onValueChange = onComentarioChange,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(2.dp))
            uiState.errorMessage?.let {
                Text(text = it, color = androidx.compose.ui.graphics.Color.Red)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { onSaveReseña() }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Guardar")
                    Text("Guardar")
                }
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { onNuevoReseña() }
                ) {
                    Icon(Icons.Default.Create, contentDescription = "Nuevo")
                    Text("Nuevo")
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteDropdown(
    uiState: UiState,
    onClienteChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Selecciona Cliente") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            label = { Text("Cliente") }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            uiState.clientes.forEach { cliente ->
                DropdownMenuItem(
                    text = { Text(cliente.nombre) },
                    onClick = {
                        selectedText = cliente.nombre
                        onClienteChange(cliente.clienteId)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoDropdown(
    uiState: UiState,
    onProductoChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Selecciona Producto") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            label = { Text("Producto") }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            uiState.productos.forEach { producto ->
                DropdownMenuItem(
                    text = { Text(producto.nombreProducto) },
                    onClick = {
                        selectedText = producto.nombreProducto
                        onProductoChange(producto.productoId)
                        expanded = false
                    }
                )
            }
        }
    }
}
