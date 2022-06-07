package pl.edu.pja.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import pl.edu.pja.AppDatabase
import pl.edu.pja.Database
import pl.edu.pja.Datasource
import pl.edu.pja.models.ItemModel
import pl.edu.pja.R
import pl.edu.pja.models.ItemEntity
import pl.edu.pja.ui.theme.WhishlistTheme

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(navController = rememberNavController())
}

@Composable
fun MainScreen(navController: NavController) {
    val applicationContext = LocalContext.current
    Database.db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "prm2"
    )
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

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
                    onClick = {
                        navController.navigate(
                            Screen.AddEditScreen.urlFromArgs(
                                "ADD",
                                -1
                            )
                        )
                    },
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
                ItemList(products = Database.db.itemDao().getAll(), navController)
            }
        }
    }
}

@Composable
fun ItemList(products: List<ItemEntity>, navController: NavController) {
    LazyColumn {
        items(products) { product ->
            Item(
                product = product,
                navController = navController
            )
        }
    }
}

@Composable
fun Item(product: ItemEntity, navController: NavController) {

    Card(
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp)
            .clickable {
                navController.navigate(Screen.AddEditScreen.urlFromArgs("EDIT", product.uid))
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ferrari),
                contentDescription = "chair",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RectangleShape)
                    .padding(2.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = product.productDescription)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = product.address)
            }
        }
    }

}