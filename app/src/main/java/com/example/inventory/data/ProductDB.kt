package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ProductDB : RoomDatabase() {

    abstract fun productDao(): ProductDao


    private class ProductCallBack(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {

                    var productDao = database.productDao()

                    //Delete all content
                    productDao.deleteAllItems()

                    var product = Product("Oil", 5, 10, "Avedov")
                    productDao.insert(product)
                }
            }
        }
    }

    companion object {
        var INSTANCE: ProductDB? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ProductDB {
            return (INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ProductDB::class.java,
                    "product"
                )
                    .build()
            })
        }
    }
}