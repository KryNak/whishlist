package pl.edu.pja.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pl.edu.pja.models.ItemEntity

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun getAll(): List<ItemEntity>

    @Query("SELECT * FROM item WHERE uid IN (:itemIds)")
    fun loadAllByIds(itemIds: IntArray): List<ItemEntity>

    @Query("SELECT * FROM item WHERE product_description LIKE :productDescription AND " +
            "address LIKE :address LIMIT 1")
    fun findByName(productDescription: String, address: String): ItemEntity

    @Query("SELECT * FROM item WHERE uid = :itemId")
    fun findById(itemId: Int): ItemEntity?

    @Query("DELETE FROM item WHERE uid = :itemId")
    fun deleteById(itemId: Int)

    @Query("UPDATE item SET product_description = :productDesc, address = :address WHERE uid = :itemId")
    fun updateById(itemId: Int, address: String, productDesc: String)

    @Insert
    fun insertAll(vararg items: ItemEntity)

    @Delete
    fun delete(item: ItemEntity)
}