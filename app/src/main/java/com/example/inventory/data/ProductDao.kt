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

    @Query("DELETE  FROM table_product")
    fun deleteAllItems()

    @Query("SELECT * FROM table_product ORDER BY id DESC")
    fun getAllItems(): LiveData<List<Product>>

}