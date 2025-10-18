package dev.jeff.practica1moviles22200127.presentation.activity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import java.text.DecimalFormat

// --- Modelos simples en el mismo archivo para esta práctica ---
private enum class ActivityType(val label: String, val calPerMin: Double) {
    CORRER("Correr", 10.0),
    CAMINAR("Caminar", 5.0),
    NADAR("Nadar", 8.0),
    CICLISMO("Ciclismo", 7.0),
    YOGA("Yoga", 4.0),
}
private enum class Intensity(val label: String, val factor: Double) {
    BAJA("Baja", 0.8),
    MEDIA("Media", 1.0),
    ALTA("Alta", 1.2),
}
private data class ActivityEntry(
    val type: ActivityType,
    val minutes: Int,
    val intensity: Intensity,
    val calories: Double
)

@Composable
fun ActivityLogScreen(onBackToMenu: () -> Unit) {
    // Estados de entrada
    var selectedType by remember { mutableStateOf(ActivityType.CORRER) }
    var minutesInput by remember { mutableStateOf("") }
    var selectedIntensity by remember { mutableStateOf(Intensity.MEDIA) }

    // Lista en memoria de actividades registradas
    val entries = remember { mutableStateListOf<ActivityEntry>() }

    // Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val df = remember { DecimalFormat("#,##0.0") }
    val total = entries.sumOf { it.calories }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text("🏃 Registro de actividad física", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))

            // Tipo de actividad (Dropdown)
            var expanded by remember { mutableStateOf(false) }
            Text("Tipo de actividad")
            Spacer(Modifier.height(6.dp))
            Box {
                OutlinedButton(onClick = { expanded = true }) {
                    Text(selectedType.label)
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    ActivityType.values().forEach { t ->
                        DropdownMenuItem(
                            text = { Text(t.label) },
                            onClick = { selectedType = t; expanded = false }
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Duración (minutos)
            OutlinedTextField(
                value = minutesInput,
                onValueChange = { minutesInput = it },
                label = { Text("Duración (min)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))

            // Intensidad (RadioButtons)
            Text("Intensidad")
            Spacer(Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Intensity.values().forEach { i ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedIntensity == i,
                            onClick = { selectedIntensity = i }
                        )
                        Text(i.label)
                        Spacer(Modifier.width(12.dp))
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Botón Registrar
            Button(
                onClick = {
                    val mins = minutesInput.toIntOrNull()
                    when {
                        minutesInput.isBlank() -> showSnack(scope, snackbarHostState, "Completa la duración")
                        mins == null || mins <= 0 -> showSnack(scope, snackbarHostState, "La duración debe ser un entero positivo")
                        else -> {
                            val calories = selectedType.calPerMin * mins * selectedIntensity.factor
                            entries += ActivityEntry(
                                type = selectedType,
                                minutes = mins,
                                intensity = selectedIntensity,
                                calories = calories
                            )
                            minutesInput = "" // limpiar campo
                            showSnack(scope, snackbarHostState, "Actividad registrada ✓")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar actividad")
            }

            Spacer(Modifier.height(12.dp))
            Divider()
            Spacer(Modifier.height(12.dp))

            // Lista
            if (entries.isEmpty()) {
                Text("Aún no registras actividades.")
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(entries) { e ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(Modifier.weight(1f)) {
                                    Text("${e.type.label} • ${e.intensity.label}")
                                    Text("Duración: ${e.minutes} min")
                                }
                                Text("${df.format(e.calories)} kcal")
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Divider()
            Spacer(Modifier.height(8.dp))

            Text("Total de calorías: ${df.format(total)} kcal", style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(16.dp))
            OutlinedButton(onClick = onBackToMenu, modifier = Modifier.fillMaxWidth()) {
                Text("⬅️ Volver al Menú")
            }
        }
    }
}

private fun showSnack(scope: CoroutineScope, host: SnackbarHostState, msg: String) {
    scope.launch {
        host.currentSnackbarData?.dismiss()
        host.showSnackbar(msg)
    }
}
