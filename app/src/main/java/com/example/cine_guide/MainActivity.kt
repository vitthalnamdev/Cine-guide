package com.example.cine_guide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cine_guide.Repository.ProductRepositoryImpl
import com.example.cine_guide.Repository.searchrepository
import com.example.cine_guide.Retrofit.Retrofit_object
import com.example.cine_guide.presentation.productsViewmodel
import com.example.cine_guide.presentation.searchviewmodel
import com.example.cine_guide.ui.Dashboard
import com.example.cine_guide.ui.ImageWithTextOverlay
import com.example.cine_guide.ui.Searchui
import com.example.cine_guide.ui.theme.CineGuideTheme
import com.example.cine_guide.ui.theme.Typography


class MainActivity : ComponentActivity() {
    var text = mutableStateOf("")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineGuideTheme {
                  App()
            }
        }
    }






    @Composable
    fun App(){
        var text by remember { mutableStateOf("") }
        val navController = rememberNavController()
         val viewModel by viewModels<productsViewmodel>(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return productsViewmodel(ProductRepositoryImpl(Retrofit_object.api))
                            as T
                }
            }
        })


        NavHost(navController = navController, startDestination = "intro"){
            composable(route = "intro"){
                ImageWithTextOverlay(navController)
            }
            composable(route = "activity"){
                   Dashboard(viewModel, navController )
            }
             composable(route = "searchview"){
                  Searchui()
             }
        }
    }




}





