package pl.edu.pja.screens

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object AddEditScreen : Screen("add_edit_screen") {
        fun urlFromArgs(mode: String, id: Int): String {
            return "$route/$mode/$id"
        }
    }
}
