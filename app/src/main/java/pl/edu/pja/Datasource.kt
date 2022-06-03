package pl.edu.pja

import pl.edu.pja.models.ItemModel

object Datasource {
    val items: MutableList<ItemModel> = mutableListOf(
        ItemModel(1, "Sofa", "ul. Makowa 8, Ostroleka Polska", R.drawable.sofa),
        ItemModel(2, "Chair", "ul. Koscielna 8", R.drawable.chair),
        ItemModel(3, "Ferrari", "ul. Wybicka 8", R.drawable.ferrari),
        ItemModel(4, "Apartment", "ul. Zlota 44", R.drawable.apartment)
    )
}