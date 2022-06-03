package pl.edu.pja.screens

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object AddScreen: Screen("add_screen")
}
