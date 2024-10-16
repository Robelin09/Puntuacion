package edu.ucne.puntuacion.presentation.cliente


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
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
fun ClienteDetailsScreen(
    clienteId: Int,
    viewModel: ClienteViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    LaunchedEffect(clienteId) {
        viewModel.getCliente(clienteId)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ClienteDetailsBodyScreen(
        uiState = uiState,
        onNombreChange = viewModel::onNombreChange,
        onDireccionChange = viewModel::onDireccionChange,
        onTelefonoChange = viewModel::onTelefonoChange,
        onSaveCliente = viewModel::save,
        onDeleteCliente = viewModel::delete,
        goBack = goBack
    )
}

@Composable
fun ClienteDetailsBodyScreen(
    uiState: UiState,
    onNombreChange: (String) -> Unit,
    onDireccionChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit,
    onSaveCliente: () -> Unit,
    onDeleteCliente: () -> Unit,
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
                label = { Text("Nombre") },
                value = uiState.nombre,
                onValueChange = onNombreChange,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                label = { Text("Dirección") },
                value = uiState.direccion,
                onValueChange = onDireccionChange,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                label = { Text("Teléfono") },
                value = uiState.telefono,
                onValueChange = onTelefonoChange,
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
                        onSaveCliente()
                    }
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Actualizar")
                    Text("Actualizar")
                }
                OutlinedButton(
                    onClick = {
                        onDeleteCliente()
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
