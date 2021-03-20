package com.example.inventory.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert
    fun insert(item: Product)

    @Update
    fun update(item: Product)

    @Delete
    fun delete(item: Product)

    @Query("DELETE FROM product")
    fun deleteAllItems()

    @Query("SELECT * FROM product ORDER BY id DESC")
    fun getAllItems(): Flow<List<Product>>

    @Query("SELECT * FROM PRODUCT WHERE id=:id ")
    fun getById(id: String): LiveData<Product>
}