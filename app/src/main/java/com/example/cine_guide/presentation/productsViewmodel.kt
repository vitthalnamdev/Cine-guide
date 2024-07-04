package com.example.cine_guide.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cine_guide.Repository.ProductRepository
import com.example.cine_guide.models.Product
import com.example.cine_guide.movieresult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class productsViewmodel(
  private val productsRepository: ProductRepository
):ViewModel(){
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()
    private val _showerror = Channel<Boolean>()
    val showerror  = _showerror.receiveAsFlow()

    init {
        viewModelScope.launch {
            productsRepository.getProductList().collectLatest {result->
                when(result){
                    is movieresult.Error ->{
                       _showerror.send(true)
                    }
                    is movieresult.Success ->{
                         result.data?.let{ products->
                             _products.update { products }
                         }
                    }
                }
            }
        }
    }
}