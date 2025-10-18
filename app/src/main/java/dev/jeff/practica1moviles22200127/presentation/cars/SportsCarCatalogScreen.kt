package dev.jeff.practica1moviles22200127.presentation.cars

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.jeff.practica1moviles22200127.model.Car
import java.text.DecimalFormat

@Composable
fun SportsCarCatalogScreen(onBackToMenu: () -> Unit) {
    val df = remember { DecimalFormat("#,##0.00") }

    // Mock data (mínimo 5 autos)
    val cars = remember {
        listOf(
            Car("Ferrari",   "488 GTB",      285000.0, "https://cdn.pixabay.com/photo/2019/11/02/16/41/ferrari-4596797_1280.jpg"),
            Car("Lamborghini","Huracán EVO", 261000.0, "https://www.eliterent.com/wp-content/uploads/2021/05/WEB_Lambo_Huracan_EVO_Spyder_Front.jpg"),
            Car("Porsche",   "911 Turbo S",  215000.0, "https://cdn.pixabay.com/photo/2019/05/26/22/58/porsche-4231489_1280.jpg"),
            Car("McLaren",   "720S",         299000.0, "https://cdn.pixabay.com/photo/2019/05/23/02/21/mclaren-4223024_1280.jpg"),
            Car("Aston Martin","Vantage",    185000.0, "https://cdn.pixabay.com/photo/2017/04/23/19/06/car-2254670_1280.jpg")
        )
    }

    val total = cars.sumOf { it.price }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("🏎️ Catálogo de autos deportivos",
            style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cars) { car ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        AsyncImage(
                            model = car.imageUrl,
                            contentDescription = "${car.brand} ${car.model}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text("${car.brand} ${car.model}",
                            style = MaterialTheme.typography.titleMedium)
                        Text("Precio aprox.: S/. ${df.format(car.price)}")
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))
        Divider()
        Spacer(Modifier.height(8.dp))

        Text("Total: S/. ${df.format(total)}", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(16.dp))
        OutlinedButton(onClick = onBackToMenu, modifier = Modifier.fillMaxWidth()) {
            Text("⬅️ Volver al Menú")
        }
    }
}
