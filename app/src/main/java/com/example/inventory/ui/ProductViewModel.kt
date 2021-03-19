package com.example.inventory.ui

import androidx.lifecycle.*
import com.example.inventory.data.Product
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository): ViewModel() {

    public val allProducts: LiveData<List<Product>> = productRepository.allProducts.asLiveData()

    fun insert(product: Product)= viewModelScope.launch {
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

class ProductViewModelFactory(private val repository: ProductRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(ProductRepository::class.java)){
                @Suppress("UNCHECKED_CAST")
                return ProductViewModel(repository) as T
            }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}