package dev.jeff.practica1moviles22200127.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.jeff.practica1moviles22200127.presentation.menu.MenuScreen
import dev.jeff.practica1moviles22200127.presentation.water.WaterIntakeScreen
import dev.jeff.practica1moviles22200127.presentation.activity.ActivityLogScreen
import dev.jeff.practica1moviles22200127.presentation.cars.SportsCarCatalogScreen

object Routes {
    const val MENU = "menu"
    const val WATER = "water"
    const val ACTIVITY = "activity"
    const val CARS = "cars"
}

@Composable
fun AppNavGraph(startDestination: String = Routes.MENU) {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = startDestination) {

        composable(Routes.MENU) {
            MenuScreen(
                onGoToWater = { nav.navigate(Routes.WATER) },
                onGoToActivity = { nav.navigate(Routes.ACTIVITY) },
                onGoToCars = { nav.navigate(Routes.CARS) }
            )
        }

        composable(Routes.WATER) {
            WaterIntakeScreen(
                onBackToMenu = {
                    nav.navigate(Routes.MENU) {
                        popUpTo(Routes.MENU) { inclusive = false }
                    }
                }
            )
        }

        composable(Routes.ACTIVITY) {
            ActivityLogScreen(
                onBackToMenu = {
                    nav.navigate(Routes.MENU) {
                        popUpTo(Routes.MENU) { inclusive = false }
                    }
                }
            )
        }

        composable(Routes.CARS) {
            SportsCarCatalogScreen(
                onBackToMenu = {
                    nav.navigate(Routes.MENU) {
                        popUpTo(Routes.MENU) { inclusive = false }
                    }
                }
            )
        }
    }
}
