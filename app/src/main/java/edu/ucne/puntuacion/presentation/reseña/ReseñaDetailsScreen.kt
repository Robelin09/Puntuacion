package edu.ucne.puntuacion.presentation.reseña

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ReseñaDetailsScreen(
    reseñaId: Int,
    viewModel: ReseñaViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    LaunchedEffect(reseñaId) {
        viewModel.getResena(reseñaId)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ReseñaDetailsBodyScreen(
        uiState = uiState,
        onClienteChange = viewModel::onClienteChange,
        onProductoChange = viewModel::onProductoChange,
        onPuntuacionChange = viewModel::onPuntuacionChange,
        onComentarioChange = viewModel::onComentarioChange,
        onSaveReseña = viewModel::save,
        onDeleteReseña = viewModel::delete,
        goBack = goBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReseñaDetailsBodyScreen(
    uiState: UiState,
    onClienteChange: (Int) -> Unit,
    onProductoChange: (Int) -> Unit,
    onPuntuacionChange: (Int) -> Unit,
    onComentarioChange: (String) -> Unit,
    onSaveReseña: () -> Unit,
    onDeleteReseña: () -> Unit,
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
                        onSaveReseña()
                    }
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Actualizar")
                    Text("Actualizar")
                }
                OutlinedButton(
                    onClick = {
                        onDeleteReseña()
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
