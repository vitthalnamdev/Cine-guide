package com.example.cine_guide.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.cine_guide.models.Product
import com.example.cine_guide.presentation.productsViewmodel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Dashboard(viewModel: productsViewmodel, navController: NavController) {

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
    Column(modifier = Modifier.fillMaxSize()) {
        MyTopAppBar(navController)
        if (productList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Spacer(modifier = Modifier.size(5.dp))
            Movies(productList)
        }
    }
}

@Composable
fun Movies(productList: List<Product>) {
    val liststate = LazyListState()
    LaunchedEffect(Unit) {
        liststate.scrollToItem(0)
    }

    LazyColumn(state = liststate){
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(navController: NavController) {
    TopAppBar(
        title = { Text("Cine Guide") },
        navigationIcon = {
            IconButton(onClick = { /* Handle navigation icon click */ }) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }
        },
        colors = TopAppBarColors(
            Color.Red,
            actionIconContentColor = Color.Black,
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black,
            scrolledContainerColor = Color.Black
        ),
        actions = {
            IconButton(onClick = { navController.navigate("searchview") }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
            IconButton(onClick = { /* Handle more icon click */ }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "More")
            }
        }
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