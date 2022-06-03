package pl.edu.pja.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pl.edu.pja.Datasource
import pl.edu.pja.ItemModel
import pl.edu.pja.R
import pl.edu.pja.ui.theme.WhishlistTheme

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(navController = rememberNavController())
}

@Composable
fun MainScreen(navController: NavController) {
    WhishlistTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Whishlist")
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.AddScreen.route) },
                    backgroundColor = MaterialTheme.colors.primary,
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                )
            }
        ) { contentPadding ->
            Surface(
                modifier = Modifier.padding(contentPadding)
            ) {
                ItemList(products = Datasource.items)
            }
        }
    }
}

@Composable
fun ItemList(products: List<ItemModel>) {
    LazyColumn {
        items(products) { product ->
            Item(
                destination = product.address,
                name = product.name,
                image = product.image
            )
        }
    }
}

@Composable
fun Item(destination: String, name: String, image: Int) {
    Card(
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = "chair",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RectangleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = name)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = destination)
            }
        }
    }
}