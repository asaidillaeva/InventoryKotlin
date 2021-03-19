package com.example.inventory.ui

import androidx.annotation.WorkerThread
import com.example.inventory.data.Product
import com.example.inventory.data.ProductDao
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {

    val allProducts: Flow<List<Product>> = productDao.getAllItems()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(product: Product){
        productDao.insert(product)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(product: Product){
        productDao.delete(product)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll(){
        productDao.deleteAllItems()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(product: Product){
        productDao.update(product)
    }

}