package dev.jeff.practica1moviles22200127.presentation.menu

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuScreen(
    onGoToWater: () -> Unit,
    onGoToActivity: () -> Unit,
    onGoToCars: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Menú Principal 🏠", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(24.dp))

        Button(onClick = onGoToWater, modifier = Modifier.fillMaxWidth()) {
            Text("💧 Calculadora de consumo de agua")
        }
        Spacer(Modifier.height(12.dp))
        Button(onClick = onGoToActivity, modifier = Modifier.fillMaxWidth()) {
            Text("🏃 Registro de actividad física")
        }
        Spacer(Modifier.height(12.dp))
        Button(onClick = onGoToCars, modifier = Modifier.fillMaxWidth()) {
            Text("🏎️ Catálogo de autos deportivos")
        }
    }
}
