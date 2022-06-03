package pl.edu.pja.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pl.edu.pja.ui.theme.WhishlistTheme


@Preview
@Composable
fun AddScreenPreview() {
    AddScreen(rememberNavController())
}

@Composable
fun AddScreen(navController: NavController) {
    AppScaffold()
}

@Composable
fun AppScaffold() {
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
                Body()
            }
        }
    }
}

@Composable
fun Body() {

    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

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
            value = location,
            onValueChange = {
                location = it
            },
            label = { Text(text = "Location") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Add")
        }
    }
}