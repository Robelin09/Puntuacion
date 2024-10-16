package edu.ucne.puntuacion.presentation.navigation


import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.puntuacion.NavigationItem
import edu.ucne.puntuacion.presentation.cliente.ClienteDetailsScreen
import edu.ucne.puntuacion.presentation.cliente.ClienteListScreen
import edu.ucne.puntuacion.presentation.cliente.ClienteScreen
import edu.ucne.puntuacion.presentation.producto.ProductoDetailsScreen
import edu.ucne.puntuacion.presentation.producto.ProductoListScreen
import edu.ucne.puntuacion.presentation.producto.ProductoScreen
import edu.ucne.puntuacion.presentation.reseña.ReseñaDetailsScreen
import edu.ucne.puntuacion.presentation.reseña.ReseñaListScreen
import edu.ucne.puntuacion.presentation.reseña.ReseñaScreen
import kotlinx.coroutines.launch

@Composable
fun PuntuacionNavHost(
    navHostController: NavHostController,
    items: List<NavigationItem>
){
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var SelectedItem by rememberSaveable {
        mutableStateOf(0)
    }
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text(text = "Menu")
                items.forEachIndexed { index, navigationItem ->
                    NavigationDrawerItem(
                        label = { Text(text = navigationItem.titulo) },
                        selected = index == SelectedItem,
                        onClick = {
                            scope.launch { drawerState.close() }
                            when(navigationItem.titulo){
                                "Clientes" -> {navHostController.navigate(Screen.ClienteList)}
                                "Reseñas" -> {navHostController.navigate(Screen.ReseñaList)}
                                "Productos" -> {navHostController.navigate(Screen.ProductoList)}
                            }
                        }
                    )
                }
            }
        },
        drawerState = drawerState
    )
    {
        NavHost(
            navController = navHostController,
            startDestination = Screen.ProductoList
        )
        {
            composable<Screen.Producto> {
                val args = it.toRoute<Screen.Producto>()
                ProductoScreen(
                    goBack = {
                        navHostController.navigateUp()
                    }
                )
            }
            composable<Screen.Cliente> {
                val args = it.toRoute<Screen.Cliente>()
                ClienteScreen(
                    goBack = {
                        navHostController.navigateUp()
                    }
                )
            }
            composable<Screen.Reseña> {
                val args = it.toRoute<Screen.Reseña>()
                ReseñaScreen(
                    goBack = {
                        navHostController.navigateUp()
                    }
                )
            }
            composable<Screen.ProductoList>{
                ProductoListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    createProducto = {
                        navHostController.navigate(Screen.Producto(0))
                    },
                    onProductoClick = {
                        navHostController.navigate(Screen.ProductoDetails(it))
                    }
                )
            }
            composable<Screen.ClienteList>{
                ClienteListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    createCliente = {
                        navHostController.navigate(Screen.Cliente(0))
                    },
                    onClienteClick = {
                        navHostController.navigate(Screen.ClienteDetails(it))
                    }

                )
            }
            composable<Screen.ReseñaList>{
                ReseñaListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    createReseña = {
                        navHostController.navigate(Screen.Reseña(0))
                    },
                    onReseñaClick = {
                        navHostController.navigate(Screen.ReseñaDetails(it))
                    }
                )
            }

            composable<Screen.ClienteDetails> { backStackEntry ->
                val args = backStackEntry.toRoute<Screen.ClienteDetails>()
                ClienteDetailsScreen(
                    clienteId = args.clienteId,
                    goBack = { navHostController.navigateUp() }
                )
            }

            composable<Screen.ProductoDetails> { backStackEntry ->
                val args = backStackEntry.toRoute<Screen.ProductoDetails>()
                ProductoDetailsScreen(
                    productoId = args.productoId,
                    goBack = { navHostController.navigateUp() }
                )
            }

            composable<Screen.ReseñaDetails> { backStackEntry ->
                val args = backStackEntry.toRoute<Screen.ReseñaDetails>()
                ReseñaDetailsScreen(
                    reseñaId = args.reseñaId,
                    goBack = { navHostController.navigateUp() }
                )
            }
        }
    }
}