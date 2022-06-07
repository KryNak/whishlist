package pl.edu.pja

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.edu.pja.daos.ItemDao
import pl.edu.pja.models.ItemEntity

@Database(entities = [ItemEntity::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}

object Database {

    lateinit var db: AppDatabase

}