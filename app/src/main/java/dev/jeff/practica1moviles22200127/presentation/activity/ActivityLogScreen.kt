package dev.jeff.practica1moviles22200127.presentation.activity

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ActivityLogScreen(onBackToMenu: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text("🏃 Registro de actividad física", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(24.dp))
        OutlinedButton(onClick = onBackToMenu, modifier = Modifier.fillMaxWidth()) {
            Text("⬅️ Volver al Menú")
        }
    }
}
