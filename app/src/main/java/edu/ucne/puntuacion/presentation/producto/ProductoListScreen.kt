package edu.ucne.puntuacion.presentation.producto

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.puntuacion.data.remote.dto.ProductoDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ProductoListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: ProductoViewModel = hiltViewModel(),
    onProductoClick: (Int) -> Unit,
    createProducto: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ProductoListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState,
        onProductoClick,
        createProducto
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoListBodyScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: UiState,
    onProductoClick: (Int) -> Unit,
    createProducto: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Lista de Productos")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Ir al Menú")
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = createProducto,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir Producto")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(35.dp))
            Text("Lista de Productos", style = MaterialTheme.typography.headlineMedium)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    modifier = Modifier.weight(1.5f),
                    text = "ID",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    modifier = Modifier.weight(3.5f),
                    text = "Nombre",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    modifier = Modifier.weight(2f),
                    text = "Precio",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            HorizontalDivider()
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.productos) { producto ->
                    ProductoRow(producto, onProductoClick)
                }
            }
        }
    }
}

@Composable
fun ProductoRow(
    producto: ProductoDto,
    onProductoClick: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onProductoClick(producto.productoId) }
            .padding(8.dp)
    ) {
        Text(modifier = Modifier.weight(1.5f), text = producto.productoId.toString())
        Text(
            modifier = Modifier.weight(3.5f),
            text = producto.nombreProducto,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(modifier = Modifier.weight(2f), text = producto.precio.toString())
    }
    HorizontalDivider()
}
