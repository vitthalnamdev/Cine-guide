package com.example.cine_guide.presentation

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cine_guide.Repository.ProductRepository
import com.example.cine_guide.Repository.searchrepository
import com.example.cine_guide.models.Product
import com.example.cine_guide.movieresult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class searchviewmodel(
    private val productrespository : searchrepository
) : ViewModel(){
     val _query = MutableLiveData<String>()
     val query :LiveData<String> = _query
     var check:Boolean = false
      val _product_list=MutableStateFlow<List<Product>>(emptyList())
      var productslist = _product_list.asStateFlow()

    private val _showerror = Channel<Boolean>()
    val showerror  = _showerror.receiveAsFlow()

    fun getmovies(){
        viewModelScope.launch {
             productrespository.getProductList(_query.value)
                 .collectLatest {
                 when(it){
                     is movieresult.Error -> {
                         _showerror.send(true)
                     }
                     is movieresult.Success -> {
                            it.data?.let{ products->
                                 _product_list.update { products }

                            }
                     }
                 }
             }
        }
    }


}
