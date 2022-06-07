package pl.edu.pja.screens

import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pl.edu.pja.Database
import pl.edu.pja.Datasource
import pl.edu.pja.R
import pl.edu.pja.models.ItemEntity
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
                Body(
                    navController = navController,
                    mode = mode,
                    itemId = itemId
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Body(
    navController: NavController,
    mode: String,
    itemId: Int
) {

    val focusManager = LocalFocusManager.current

    var description by remember {
        mutableStateOf(
            Database.db.itemDao().findById(itemId)?.productDescription ?: ""
        )
    }
    var address by remember {
        mutableStateOf(
            Database.db.itemDao().findById(itemId)?.address ?: ""
        )
    }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    var markerPosition = LatLng(52.223830, 20.993940)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerPosition, 15f)
    }

    val localContext = LocalContext.current

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    onMapClick = {
                        val fromLocation =
                            Geocoder(localContext).getFromLocation(it.latitude, it.longitude, 1)[0]
                        val newAddress =
                            "ul. ${fromLocation.getAddressLine(0)}"
                        Toast.makeText(
                            localContext,
                            "Twoja lokalizacja zostanie ustawiona na $newAddress",
                            Toast.LENGTH_SHORT
                        ).show()
                        address = newAddress
                        Log.i("Debug", "$fromLocation")
                    }
                ) {
                    Marker(
                        position = markerPosition,
                        title = "Reference position",
                        snippet = "Polish Japanese Academy of Information Technology"
                    )
                }
                IconButton(onClick = {
                    if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }
                },
                    modifier = Modifier.clickable {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .background(Color.White, shape = CircleShape)
                            .clip(CircleShape)
                    )
                }
            }

        },
        sheetPeekHeight = 0.dp,
        sheetGesturesEnabled = false,
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures {
                focusManager.clearFocus()
            }
        }
    ) {

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
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = address,
                onValueChange = {
                    focusManager.clearFocus(true)
                },
                label = { Text(text = "Location") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = false) {},
                trailingIcon = {
                    Icon(imageVector = Icons.Default.LocationOn,
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier.clickable {
                            coroutineScope.launch {

                                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                } else {
                                    bottomSheetScaffoldState.bottomSheetState.collapse()
                                }

                            }
                        }
                    )
                }
            )
            when (mode) {
                "ADD" -> AddRow(navController = navController, address, description)
                "EDIT" -> EditRow(navController = navController, address, description, itemId)
            }
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
                Database.db.itemDao().deleteById(itemId)
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
                Datasource.items.add(
                    ItemModel(
                        itemId,
                        description,
                        address,
                        foundedProduct?.image ?: R.drawable.ic_add
                    )
                )
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
                Database.db.itemDao().insertAll(ItemEntity(productDescription = description, address = address))
                navController.navigate(Screen.MainScreen.route) { popUpTo(0) }
            },
        ) {
            Text(text = "Add")
        }
    }
}