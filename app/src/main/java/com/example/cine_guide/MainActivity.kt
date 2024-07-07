package com.example.cine_guide

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.cine_guide.Repository.ProductRepositoryImpl
import com.example.cine_guide.Retrofit.Retrofit_object
import com.example.cine_guide.models.Product
import com.example.cine_guide.presentation.productsViewmodel
import com.example.cine_guide.ui.theme.CineGuideTheme
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<productsViewmodel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return productsViewmodel(ProductRepositoryImpl(Retrofit_object.api))
                        as T
            }
        }
    })

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
        val navController = rememberNavController()
         NavHost(navController = navController, startDestination = "intro"){
            composable(route = "intro"){
                ImageWithTextOverlay(navController)
            }
            composable(route = "activity"){
                Dashboard()
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
        fun Dashboard() {
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
                    Search()

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
        fun Search() {
            var text by remember { mutableStateOf("") }
            TextField(
                value = text,
                placeholder = { Text("Search...") },
                onValueChange = { newText ->
                    text = newText
                },
                modifier = Modifier.fillMaxWidth().height(80.dp).background(Color.White)
                    .padding(top = 28.dp, start = 14.dp, end = 14.dp),
                shape = RoundedCornerShape(16.dp)
            )

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





