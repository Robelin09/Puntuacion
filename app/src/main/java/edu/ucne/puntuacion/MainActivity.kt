package edu.ucne.puntuacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Info
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.puntuacion.presentation.navigation.PuntuacionNavHost
import edu.ucne.puntuacion.ui.theme.PuntuacionTheme

@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PuntuacionTheme {
                val navHost = rememberNavController()
                val items = NavigationItems()
                PuntuacionNavHost(navHost, items)
            }
        }
    }
}

fun NavigationItems():List<NavigationItem>{
    return listOf(
        NavigationItem(
            titulo = "Clientes",
            SelectIcon = Icons.Filled.Info,
            UnSelectIcon = Icons.Outlined.Info
        ),
        NavigationItem(
            titulo = "Reseñas",
            SelectIcon = Icons.Filled.Build,
            UnSelectIcon = Icons.Outlined.Build
        ),
        NavigationItem(
            titulo = "Productos",
            SelectIcon = Icons.Filled.Info,
            UnSelectIcon = Icons.Outlined.Info
        )
    )
}