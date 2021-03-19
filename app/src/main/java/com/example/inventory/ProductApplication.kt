package com.example.inventory

import android.app.Application
import com.example.inventory.data.ProductDB
import com.example.inventory.ui.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ProductApplication: Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())


    private val database by lazy {ProductDB.getDatabase(this, applicationScope)}
    val repository by lazy {ProductRepository(database.productDao())}

}