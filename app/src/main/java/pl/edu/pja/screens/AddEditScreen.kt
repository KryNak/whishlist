package pl.edu.pja.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pl.edu.pja.Datasource
import pl.edu.pja.R
import pl.edu.pja.models.ItemModel
import pl.edu.pja.ui.theme.WhishlistTheme


@Preview
@Composable
fun AddScreenPreview() {
    AddEditScreen(rememberNavController(), "ADD", -1)
}

@Preview
@Composable
fun EditScreenPreview() {
    AddEditScreen(rememberNavController(), "EDIT", 1)
}

@Composable
fun AddEditScreen(navController: NavController, mode: String, itemId: Int) {
    AppScaffold(navController, mode, itemId)
}

@Composable
fun AppScaffold(navController: NavController, mode: String, itemId: Int) {
    WhishlistTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Whishlist")
                    }
                )
            }
        ) { contentPadding ->
            Surface(
                modifier = Modifier.padding(contentPadding)
            ) {
                Body(navController, mode, itemId)
            }
        }
    }
}

@Composable
fun Body(navController: NavController, mode: String, itemId: Int) {

    var description by remember {
        mutableStateOf(
            Datasource.items.find { it.id == itemId }?.description ?: ""
        )
    }
    var address by remember {
        mutableStateOf(
            Datasource.items.find { it.id == itemId }?.address ?: ""
        )
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
            },
            label = { Text(text = "Description") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = address,
            onValueChange = {
                address = it
            },
            label = { Text(text = "Location") },
            modifier = Modifier.fillMaxWidth()
        )
        when (mode) {
            "ADD" -> AddRow(navController = navController, address, description)
            "EDIT" -> EditRow(navController = navController, address, description, itemId)
        }
    }

}

@Composable
fun EditRow(navController: NavController, address: String, description: String, itemId: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                Datasource.items.removeIf { it.id == itemId }
                navController.navigate(Screen.MainScreen.route) { popUpTo(0) }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
        ) {
            Text(text = "Delete")
        }

        Button(
            onClick = {
                val foundedProduct = Datasource.items.find { it.id == itemId }
                Datasource.items.removeIf { it.id == itemId }
                Datasource.items.add(ItemModel(itemId, description, address, foundedProduct?.image ?: R.drawable.ic_add))
                navController.navigate(Screen.MainScreen.route) { popUpTo(0) }
            },
        ) {
            Text(text = "Edit")
        }
    }
}

@Composable
fun AddRow(navController: NavController, address: String, description: String) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                Datasource.items.add(ItemModel(Datasource.items.maxOf { it.id } + 1,
                    description,
                    address,
                    R.drawable.ic_add))
                navController.navigate(Screen.MainScreen.route) { popUpTo(0) }
            },
        ) {
            Text(text = "Add")
        }
    }
}