package pl.edu.pja

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.edu.pja.screens.AddScreen
import pl.edu.pja.screens.MainScreen
import pl.edu.pja.screens.Screen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController)
        }
        composable(route = Screen.AddScreen.route) {
            AddScreen(navController)
        }
    }
}