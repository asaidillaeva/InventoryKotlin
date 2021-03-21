package com.example.inventory.ui

import android.os.AsyncTask
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.inventory.data.Product
import com.example.inventory.data.ProductDao

class ProductRepository(private val productDao: ProductDao) {

    val allProducts: LiveData<List<Product>> = productDao.getAllItems()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(product: Product) {
        InsertItemAsyncTask(productDao)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(product: Product) {
        DeleteItemAsyncTask(productDao)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        DeleteAllItemsAsyncTask(productDao)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(product: Product) {
        UpdateItemAsyncTask(productDao)
    }

    companion object {
        private class InsertItemAsyncTask(private val itemDAO: ProductDao) :
            AsyncTask<Product, Unit, Unit>() {
            override fun doInBackground(vararg params: Product?) {
                itemDAO.insert(params[0]!!)
            }
        }

        private class UpdateItemAsyncTask(private val itemDAO: ProductDao) :
            AsyncTask<Product, Unit, Unit>() {
            override fun doInBackground(vararg params: Product?) {
                itemDAO.update(params[0]!!)
            }
        }

        private class DeleteItemAsyncTask(private val itemDAO: ProductDao) :
            AsyncTask<Product, Unit, Unit>() {
            override fun doInBackground(vararg params: Product?) {
                itemDAO.delete(params[0]!!)
            }
        }

        private class DeleteAllItemsAsyncTask(private val itemDAO: ProductDao) :
            AsyncTask<Product, Unit, Unit>() {
            override fun doInBackground(vararg params: Product?) {
                itemDAO.deleteAllItems()
            }
        }
    }
}