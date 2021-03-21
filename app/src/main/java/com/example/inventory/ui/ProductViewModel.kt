package com.example.inventory.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Product
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    val allProducts: LiveData<List<Product>> = productRepository.allProducts

    fun insert(product: Product) = viewModelScope.launch {
        productRepository.insert(product)
    }

    fun delete(product: Product) = viewModelScope.launch {
        productRepository.delete(product)
    }

    fun deleteAll() = viewModelScope.launch {
        productRepository.deleteAll()
    }

    fun update(product: Product) = viewModelScope.launch {
        productRepository.update(product)
    }


}

class ProductViewModelFactory(private val repository: ProductRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
    }


}