package com.example.cine_guide

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.cine_guide.Repository.ProductRepositoryImpl
import com.example.cine_guide.Repository.genreRepository
import com.example.cine_guide.Repository.searchrepository
import com.example.cine_guide.Retrofit.Retrofit_object
import com.example.cine_guide.modelgenre.Genre
import com.example.cine_guide.models.Product
import com.example.cine_guide.presentation.productsViewmodel
import com.example.cine_guide.ui.theme.CineGuideTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    var text =   mutableStateOf("")
    private val genre_map = genreRepository(Retrofit_object.genre_api).getGenreList()
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineGuideTheme {
                var iserror:Boolean = false
                val genrelist:MutableList<Genre> = mutableListOf<Genre>()

                 runBlocking {
                     genre_map.collect{
                         when(it){
                             is movieresult.Error -> TODO()
                             is movieresult.Success -> {
                                 val curr  = it.data
                                 if(curr!=null){
                                     for(i in curr){
                                         genrelist.add(i)
                                     }
                                 }
                             }
                         }
                     }
                 }
                var sz = genrelist.size

                val int_to_String: HashMap<Int, String> = HashMap()
                val String_to_int: HashMap<String, Int> = HashMap()
                var genre = int_to_String[28]

                for(i in genrelist){
                    int_to_String[i.id] = i.name
                    String_to_int[i.name] = i.id
                }

                App()
            }
        }
    }




    @Composable
    fun App(){
        val navController = rememberNavController()
         val viewModel by viewModels<productsViewmodel>(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return productsViewmodel(ProductRepositoryImpl(Retrofit_object.api))
                            as T
                }
            }
        })
        val searchviewModel by viewModels<productsViewmodel>(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return productsViewmodel(searchrepository(Retrofit_object.searchapi,"Avengers"))
                            as T
                }
            }
        })

         NavHost(navController = navController, startDestination = "intro"){
            composable(route = "intro"){
                ImageWithTextOverlay(navController)
            }
            composable(route = "activity"){
                   Dashboard(viewModel, navController)
            }
        }
    }

    @Composable
    fun ImageWithTextOverlay(navController: NavController) {

        Box(modifier = Modifier.fillMaxSize()) {
            // Replace with your image resource
            Image(
                painter = painterResource(id = R.drawable.strange),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Semi-transparent overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
            )
            // Text overlay
            Text(
                text = "Get to know",
                color = Color.White,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 350.dp, start = 24.dp)
            )
            Text(
                text = "About your",
                color = Color.White,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 400.dp, start = 24.dp)
            )
            Text(
                text = "Fabourite T.V",
                color = Color.White,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 450.dp, start = 24.dp)
            )
            Text(
                text = "Shows and",
                color = Color.White,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 500.dp, start = 24.dp)
            )
            Text(
                text = "Movies.",
                color = Color.White,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 550.dp, start = 24.dp)
            )

            Button(
                onClick = {
                   navController.navigate("activity")
                }, modifier = Modifier.padding(start = 45.dp, end = 45.dp, top = 690.dp)
            ) {
                Text(
                    "Get Started",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    @Composable
    fun Search(navController: NavController) {

        TextField(
            value = text.value,
            placeholder = { Text("Search...") },
            onValueChange = { newText ->
                text.value = newText
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.White)
                .padding(top = 28.dp, start = 14.dp, end = 14.dp),
            shape = RoundedCornerShape(16.dp)
        )


    }


    @Composable
        fun Dashboard(viewModel: productsViewmodel , navController: NavController) {
            val productList = viewModel.products.collectAsState().value
            val context = LocalContext.current
            LaunchedEffect(key1 = viewModel.showerror) {
                viewModel.showerror.collectLatest { show ->
                    if (show) {
                        Toast.makeText(
                            context, "Error", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            if (productList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    Search(navController)

                    LazyColumn {
                        var cnt = 0
                        var sz = productList.size
                        var ls: MutableList<Product> = mutableListOf()

                        items(productList) { item ->

                            cnt++
                            ls.add(item)
                            sz--;
                            if (sz == 0 && cnt == 1) {
                                LeftImage(ls[0])
                            }
                            if (cnt == 2) {
                                cnt = 0
                                Row() {
                                    LeftImage(ls[0])
                                    Spacer(modifier = Modifier.size(10.dp))
                                    RightImage(ls[1])
                                }
                                ls.clear()
                            }
                        }

                    }
                }
            }
        }





        @Composable
        fun LeftImage(poster: Product) {
            var basepath: String = "https://image.tmdb.org/t/p/original/"
            var path = basepath + poster.poster_path
            val imagepainter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current).data(path)
                    .size(Size.ORIGINAL).build()
            ).state
            Column {
                Card(
                    modifier = Modifier
                        .padding(top = 24.dp, start = 14.dp)
                        .height(290.dp)
                        .width(180.dp)
                        .shadow(
                            elevation = 10.dp
                        ),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                ) {
                    if (imagepainter is AsyncImagePainter.State.Success) {
                        Image(
                            painter = imagepainter.painter,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,

                            )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                }

                Card(
                    modifier = Modifier
                        .padding(start = 14.dp)
                        .height(25.dp)
                        .width(180.dp)

                        .shadow(
                            elevation = 10.dp
                        ),
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                ) {
                    Text(
                        text = poster.title,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }


        }


        @Composable
        fun RightImage(poster: Product) {
            var basepath: String = "https://image.tmdb.org/t/p/original/"
            var path = basepath + poster.poster_path
            val imagepainter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current).data(path)
                    .size(Size.ORIGINAL).build()
            ).state
            Column {
                Card(
                    modifier = Modifier
                        .padding(top = 24.dp, end = 14.dp)
                        .height(290.dp)
                        .width(180.dp)

                        .shadow(
                            elevation = 10.dp
                        ),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                ) {

                    if (imagepainter is AsyncImagePainter.State.Success) {
                        Image(
                            painter = imagepainter.painter,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,

                            )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                }
                Card(
                    modifier = Modifier
                        .padding(end = 14.dp)
                        .height(25.dp)
                        .width(180.dp)
                        .shadow(
                            elevation = 10.dp
                        ),
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                ) {
                    Text(
                        text = poster.title,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }





