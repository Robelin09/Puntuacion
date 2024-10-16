package edu.ucne.puntuacion.presentation.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ClienteScreen(
    viewModel: ClienteViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ClienteBodyScreen(
        uiState = uiState,
        onNombreChange = viewModel::onNombreChange,
        onDireccionChange = viewModel::onDireccionChange,
        onTelefonoChange = viewModel::onTelefonoChange,
        onSaveCliente = viewModel::save,
        onNuevoCliente = viewModel::nuevo,
        goBack = goBack
    )
}

@Composable
fun ClienteBodyScreen(
    uiState: UiState,
    onNombreChange: (String) -> Unit,
    onDireccionChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit,
    onSaveCliente: () -> Unit,
    onNuevoCliente: () -> Unit,
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
                    onClick = {
                        onSaveCliente()
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Guardar")
                    Text("Guardar")
                }

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onNuevoCliente()
                    }
                ) {
                    Icon(Icons.Default.Create, contentDescription = "Nuevo")
                    Text("Nuevo")
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}
