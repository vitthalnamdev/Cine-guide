package com.example.cine_guide.presentation

import androidx.lifecycle.ViewModel
import com.example.cine_guide.models.Product

class sharedviewmodel: ViewModel() {
    var poster:Product? = null
    var search:String = ""
    fun addposter(value:Product){
         poster = value
    }

}