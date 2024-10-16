package edu.ucne.puntuacion.presentation.reseña


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.puntuacion.data.remote.dto.ClienteDto
import edu.ucne.puntuacion.data.remote.dto.ProductoDto
import edu.ucne.puntuacion.data.remote.dto.ReseñaDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ReseñaListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: ReseñaViewModel = hiltViewModel(),
    onReseñaClick: (Int) -> Unit,
    createReseña: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReseñaListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState = uiState,
        onReseñaClick = onReseñaClick,
        createReseña = createReseña,
        clientes = uiState.clientes,
        productos = uiState.productos
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReseñaListBodyScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: UiState,
    onReseñaClick: (Int) -> Unit,
    createReseña: () -> Unit,
    clientes: List<ClienteDto>,
    productos: List<ProductoDto>
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Lista de Reseñas") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Ir al Menú")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = createReseña,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir Reseña")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(35.dp))
            Text("Lista de Reseñas", style = MaterialTheme.typography.headlineMedium)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "ID",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    modifier = Modifier.weight(2f),
                    text = "Cliente",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    modifier = Modifier.weight(2f),
                    text = "Producto",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Puntuación",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            Divider()
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.reseñas) { reseña ->
                    ReseñaRow(
                        reseña = reseña,
                        onReseñaClick = onReseñaClick,
                        clientes = clientes,
                        productos = productos
                    )
                }
            }
        }
    }
}

@Composable
fun ReseñaRow(
    reseña: ReseñaDto,
    onReseñaClick: (Int) -> Unit,
    clientes: List<ClienteDto>,
    productos: List<ProductoDto>
) {
    val cliente = clientes.find { it.clienteId == reseña.clienteId }
    val producto = productos.find { it.productoId == reseña.productoId }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onReseñaClick(reseña.reseñaId) }
            .padding(vertical = 8.dp)
    ) {
        Text(modifier = Modifier.weight(1f), text = reseña.reseñaId.toString())
        Text(
            modifier = Modifier.weight(2f),
            text = cliente?.nombre ?: "N/A",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(modifier = Modifier.weight(2f), text = producto?.nombreProducto ?: "N/A")
        Text(
            modifier = Modifier.weight(1f),
            text = reseña.puntuacion.toString(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
    HorizontalDivider()
}
