package pl.edu.pja

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pl.edu.pja.screens.AddEditScreen
import pl.edu.pja.screens.MainScreen
import pl.edu.pja.screens.Screen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController)
        }
        composable(
            route = "${Screen.AddEditScreen.route}/{mode}/{itemId}",
            arguments = listOf(
                navArgument("mode") { type = NavType.StringType },
                navArgument("itemId") { type = NavType.IntType }
            )
        ) { entry ->
            val itemId: Int? = entry.arguments?.getInt("itemId")
            val mode: String? = entry.arguments?.getString("mode")

            mode?.let { AddEditScreen(navController = navController, mode = mode, itemId = itemId ?: -1) }
        }
    }
}