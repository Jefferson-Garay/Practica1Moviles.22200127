package dev.jeff.practica1moviles22200127.presentation.water

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun WaterIntakeScreen(onBackToMenu: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Sin especificar") }
    var result by remember { mutableStateOf<String?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()   // 👈 scope para lanzar snackbars

    val genders = listOf("Masculino", "Femenino", "Sin especificar")
    val genderFactor = when (gender) {
        "Masculino" -> 1.02
        "Femenino" -> 1.01
        else -> 1.00
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("💧 Consumo de agua recomendado", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it; result = null },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it; result = null },
                label = { Text("Peso (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("Género: ")
                Spacer(Modifier.width(8.dp))
                genders.forEach { g ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = gender == g, onClick = { gender = g; result = null })
                        Text(g); Spacer(Modifier.width(12.dp))
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    val w = weight.toDoubleOrNull()
                    when {
                        name.isBlank() || weight.isBlank() || gender.isBlank() ->
                            showSnack(scope, snackbarHostState, "Todos los campos son obligatorios")
                        w == null ->
                            showSnack(scope, snackbarHostState, "El peso debe ser numérico")
                        w <= 5 || w > 200 ->
                            showSnack(scope, snackbarHostState, "El peso debe estar entre 5 y 200 kg")
                        else -> {
                            val liters = w * 0.035 * genderFactor
                            val rounded = String.format(Locale.getDefault(), "%.2f", liters) // 👈 sin warning
                            result = "$name debe beber aproximadamente $rounded litros de agua al día"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Calcular") }

            Spacer(Modifier.height(16.dp))
            result?.let { Text(it, style = MaterialTheme.typography.titleMedium) }

            Spacer(Modifier.height(24.dp))
            OutlinedButton(onClick = onBackToMenu, modifier = Modifier.fillMaxWidth()) {
                Text("⬅️ Volver al Menú")
            }
        }
    }
}

/** Helper para mostrar Snackbars desde un Composable sin usar LaunchedEffect */
private fun showSnack(scope: CoroutineScope, host: SnackbarHostState, msg: String) {
    scope.launch {
        host.currentSnackbarData?.dismiss()
        host.showSnackbar(message = msg)
    }
}
